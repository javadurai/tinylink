package com.github.jabadurai.tinylink.controllers;

import com.github.jabadurai.tinylink.repositories.UrlRepository;
import com.github.jabadurai.tinylink.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DashboardController {

    private UrlRepository urlRepository;

    private UserRepository userRepository;

    public DashboardController(UrlRepository urlRepository, UserRepository userRepository) {
        this.urlRepository = urlRepository;
        this.userRepository = userRepository;
    }


    @RequestMapping("/")
    public String dashboard(Model model){

        model.addAttribute("totalUsers", userRepository.totalUsers());
        model.addAttribute("numberOfActiveUsers", userRepository.numberOfActiveUsers());
        model.addAttribute("totalLinks", urlRepository.totalLinks());
        model.addAttribute("totalClicksOfLinks", urlRepository.totalClicksOfLinks());

        return "dashboard";
    }

}
