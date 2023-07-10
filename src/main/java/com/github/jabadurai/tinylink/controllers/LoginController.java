package com.github.jabadurai.tinylink.controllers;

import com.github.jabadurai.tinylink.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {


    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            errorMessage = (String) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
        if (StringUtils.isNotEmpty(errorMessage)) {
            model.addAttribute("error", errorMessage);
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        } else if (StringUtils.isNotEmpty(error)) {
            model.addAttribute("error", error);
        } else if (error != null){
            model.addAttribute("error", "Invalid username or password");
        }
        return "login";
    }
}
