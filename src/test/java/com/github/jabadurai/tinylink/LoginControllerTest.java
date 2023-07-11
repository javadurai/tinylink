package com.github.jabadurai.tinylink;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.web.WebAttributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HttpServletRequest request;

    @MockBean
    private Model model;

    @Test
    public void whenErrorIsNotEmpty_addItToModel() throws Exception {
        when(request.getSession(false)).thenReturn(null);
        mockMvc.perform(get("/login").param("error", "someError"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("login"));
    }

    @Test
    public void whenSessionErrorIsNotEmpty_addItToModelAndRemoveFromSession() throws Exception {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)).thenReturn("sessionError");
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("login"));
        verify(session).removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    @Test
    public void whenErrorAndSessionErrorAreEmpty_doNotAddErrorToModel() throws Exception {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)).thenReturn("");
        mockMvc.perform(get("/login").param("error", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(view().name("login"));
    }

    @Test
    public void whenErrorIsNull_AddInvalidUsernameOrPasswordToModel() throws Exception {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)).thenReturn("");
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("error", "Invalid username or password"))
                .andExpect(view().name("login"));
    }
}
