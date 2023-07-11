package com.github.jabadurai.tinylink;


import com.github.jabadurai.tinylink.exceptions.CustomAuthenticationFailureHandler;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomAuthenticationFailureHandlerTest {

    private CustomAuthenticationFailureHandler handler;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    public void setup() {
        handler = new CustomAuthenticationFailureHandler();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void onAuthenticationFailure_whenExceptionIsBadCredentialsException_setErrorMessage() throws IOException, ServletException {
        handler.onAuthenticationFailure(request, response, new BadCredentialsException("Bad Credentials"));
        assertEquals("Invalid username or password.", request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
        assertEquals("/login", response.getRedirectedUrl());
    }

    @Test
    public void onAuthenticationFailure_whenExceptionIsLockedException_setErrorMessage() throws IOException, ServletException {
        handler.onAuthenticationFailure(request, response, new LockedException("Locked"));
        assertEquals("Your account has been locked. Please contact the administrator.", request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
        assertEquals("/login", response.getRedirectedUrl());
    }

    // Add similar tests for other exceptions...
}