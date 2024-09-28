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

import jakarta.servlet.http.HttpSession;

import com.personal.jobhive.entities.User;
import com.personal.jobhive.helpers.Helper;

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
    public String userDashboard(Model model, Authentication authentication, HttpSession session) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        model.addAttribute("totalApplications", jobService.getAll(user.getUserId()).size());
        model.addAttribute("starredApplications", jobService.getStarredJobs(user.getUserId()).size());
        model.addAttribute("successRate", Helper.calculateSuccessPercentage(jobService.getAll(user.getUserId())));
        return "user/dashboard";
    }

    // user profile page
    @RequestMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication) {
        return "user/profile";
    }
}
