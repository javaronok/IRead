package com.iread.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final static String DEFAULT = "/catalog";

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public AuthenticationSuccessHandler() {
        super();
        //this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(DEFAULT);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        String targetUrl = DEFAULT;

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
}
