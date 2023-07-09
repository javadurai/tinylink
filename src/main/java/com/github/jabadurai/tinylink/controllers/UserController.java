package com.github.jabadurai.tinylink.controllers;

import com.github.jabadurai.tinylink.entities.User;
import com.github.jabadurai.tinylink.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping
    public String listUsers(Model model){

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "users";
    }
}
