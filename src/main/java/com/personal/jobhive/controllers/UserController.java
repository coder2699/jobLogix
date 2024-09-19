package com.personal.jobhive.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;

import com.personal.jobhive.services.JobService;
import com.personal.jobhive.services.UserService;
import com.personal.jobhive.entities.Job;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    // user dashbaord page
    @RequestMapping(value = "/dashboard")
    public String userDashboard(Model model) {
        model.addAttribute("totalApplications", jobService.getAll().size());
        model.addAttribute("starredApplications", jobService.getStarredJobs().size());
        return "user/dashboard";
    }

    // user profile page
    @RequestMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication) {
        return "user/profile";
    }
    
    // user add job page
    // user view job page
    // user edit job page
    // user delete job page
}
