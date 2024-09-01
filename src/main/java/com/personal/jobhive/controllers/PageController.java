package com.personal.jobhive.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {
    @GetMapping("/home")
    public String getHome(Model model) {
        model.addAttribute("name", "Pranshu Pandey");
        model.addAttribute("githubRepo", "https://github.com/coder2699/");
        return "home";
    }
}
