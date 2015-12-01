package com.iread.config;

import com.iread.security.AuthenticationSuccessHandler;
import com.iread.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@Import({ AppWebMVCConfig.class })
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth,
                                CustomAuthenticationProvider customAuthProvider
    ) throws Exception {
        auth.authenticationProvider(customAuthProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/fonts/**");
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/images/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/rating").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/catalog").permitAll()
                .antMatchers("/addbook").permitAll()
                .anyRequest().anonymous()
                .and()
                .formLogin()
                .loginProcessingUrl("/j_spring_security_check")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                        //.loginPage("/catalog")
                .permitAll()
                .successHandler(authenticationSuccessHandler)
                //.failureUrl("/catalog?error=true")
                .failureHandler(new SimpleUrlAuthenticationFailureHandler("/catalog"))
                .and()
                .logout()
                .logoutSuccessUrl("/catalog")
                .logoutUrl("/j_spring_security_logout");
    }
}
