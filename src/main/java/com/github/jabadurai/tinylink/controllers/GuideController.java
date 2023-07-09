package com.github.jabadurai.tinylink.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guide")
public class GuideController {

    @GetMapping
    public String userGuide(){

        return "guide";
    }
}
