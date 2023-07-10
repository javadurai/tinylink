package com.github.jabadurai.tinylink.exceptions;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;

        if (exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
            errorMessage = "Invalid username or password.";
        } else if (exception.getClass().isAssignableFrom(LockedException.class)) {
            errorMessage = "Your account has been locked. Please contact the administrator.";
        } else if (exception.getClass().isAssignableFrom(DisabledException.class)) {
            errorMessage = "Your account has been disabled. Please contact the administrator to enabled it.";
        } else if (exception.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
            errorMessage = "Invalid username or password.";
        } else {
            errorMessage = "Unknown error - " + exception.getMessage();
        }

        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
        response.sendRedirect("/login?error=true");
    }
}