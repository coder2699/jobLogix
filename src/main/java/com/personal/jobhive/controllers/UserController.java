package com.personal.jobhive.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/user")
public class UserController {
    // user dashboard page
    @GetMapping("/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }

    // user profile page
    @GetMapping("/profile")
    public String userProfile() {
        return "user/profile";
    }
    
    // user add job page
    // user view job page
    // user edit job page
    // user delete job page
}
