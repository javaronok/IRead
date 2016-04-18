package com.iread.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;


import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.iread" })
@EnableJpaRepositories(
        basePackages = {"com.iread.repository"},
        entityManagerFactoryRef = "ireadEntityManager",
        transactionManagerRef = "ireadTransactionManager")
//@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableSpringDataWebSupport
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@PropertySource(value = {"classpath:config.properties"})
public class AppWebMVCConfig extends WebMvcConfigurerAdapter {

    @Autowired
    Environment env;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/catalog").setViewName("catalog");
        registry.addViewController("/signup").setViewName("registration");
        registry.addViewController("/addbook").setViewName("add_book");

    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/pages/");
        bean.setSuffix(".jsp");
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        BCryptPasswordEncoder bean = new BCryptPasswordEncoder(10);
        return bean;
    }

    @Bean
    public static PropertyPlaceholderConfigurer placeHolderConfigurer() {
        PropertyPlaceholderConfigurer bean = new PropertyPlaceholderConfigurer();
        Resource[] resources = {
                new ClassPathResource("config.properties")
        };
        bean.setLocations(resources);
        bean.setIgnoreUnresolvablePlaceholders(true);
        return bean;
    }

    @Primary
    @Bean(name="ireadDataSource")
    public DataSource dataSource() {
        String driverClassName = env.getProperty("jdbc.iread.driverClassName");
        String databaseUrl = env.getProperty("jdbc.iread.databaseurl");
        String dbUsername = env.getProperty("jdbc.iread.username");
        String dbPassword = env.getProperty("jdbc.iread.password");

        BasicDataSource bean = new BasicDataSource();
        bean.setDriverClassName(driverClassName);
        bean.setUrl(databaseUrl);
        bean.setUsername(dbUsername);
        bean.setPassword(dbPassword);
        bean.setValidationQuery("SELECT 1 FROM RDB$DATABASE");
        bean.setTestWhileIdle(true);
        bean.setTimeBetweenEvictionRunsMillis(5000);
        bean.setMaxActive(100);
        bean.setMinIdle(10);
        bean.setMaxIdle(80);
        bean.setMaxWait(10000);
        bean.setMinEvictableIdleTimeMillis(30000);
        return bean;
    }

    public Properties hibernateProperties() {
        String dialect = env.getProperty("jdbc.iread.dialect");

        Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.dialect", dialect);
        hibernateProperties.setProperty("hibernate.show_sql", "false");
        hibernateProperties.setProperty("hibernate.format_sql", "true");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "validate");

        return hibernateProperties;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory sessionFactory(DataSource dataSource,
                                         Properties hibernateProperties) {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(
                dataSource);

        sessionBuilder.scanPackages("com.iread.model");
        sessionBuilder.addProperties(hibernateProperties());
        return sessionBuilder.buildSessionFactory();
    }

    @Primary
    @Bean(name = "ireadEntityManager")
    public LocalContainerEntityManagerFactoryBean icmEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.iread.model");
        em.setPersistenceUnitName("icmPersistenceUnit");
        em.setJpaProperties(hibernateProperties());
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return em;
    }

    public MappingJackson2HttpMessageConverter jacksonMessageConverter(){
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = new ObjectMapper();
        //Registering Hibernate4Module to support lazy objects
        mapper.registerModule(new Hibernate4Module());

        messageConverter.setObjectMapper(mapper);
        return messageConverter;

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jacksonMessageConverter());
        super.configureMessageConverters(converters);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/images/**").addResourceLocations(
                "/images/");
        registry.addResourceHandler("/libs/**").addResourceLocations("/libs/");
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("/fonts/");
    }

    @Primary
    @Bean(name = "ireadTransactionManager")
    public PlatformTransactionManager icmTransactionManager(
            EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    /*@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(tableParamArgumentResolver());
    }*/

    /*@Bean
    public HandlerMethodArgumentResolver tableParamArgumentResolver() {
        return new TableParamArgumentResolver();
    }*/

    /*@Bean
    public CommonsMultipartResolver multipartResolver() throws IOException {
        String path = System.getProperty("java.io.tmpdir");
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setMaxUploadSize(10 * 1024 * 1024); // 10Mb
        commonsMultipartResolver.setUploadTempDir(new FileSystemResource(path));
        return commonsMultipartResolver;
    }*/
}
