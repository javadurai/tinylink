package com.github.jabadurai.tinylink.controllers;

import com.github.jabadurai.tinylink.dto.ChangePasswordRequest;
import com.github.jabadurai.tinylink.service.UserService;
import com.github.jabadurai.tinylink.validators.ChangePasswordValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ProfileController {

    private final UserService userService;

    private final ChangePasswordValidator changePasswordValidator;

    public ProfileController(UserService userService, ChangePasswordValidator changePasswordValidator) {
        this.userService = userService;
        this.changePasswordValidator = changePasswordValidator;
    }

    @GetMapping("/profile")
    public String profile(){

        return "profile";
    }

    @GetMapping("/password")
    public String changePassword(){

        return "password";
    }

    @PostMapping("/password")
    public String changePassword(@ModelAttribute ChangePasswordRequest changePasswordRequest, BindingResult bindingResult, Model model) {

        changePasswordValidator.validate(changePasswordRequest, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            return "password";
        }

        userService.changePassword(changePasswordRequest);

        // Optionally, you can put a success message in the model to display on the password page
        model.addAttribute("success", "Password changed successfully");

        return "password";
    }

}
