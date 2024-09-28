package com.personal.jobhive.controllers;
import com.personal.jobhive.entities.ChartData;
import com.personal.jobhive.entities.Job;
import com.personal.jobhive.entities.User;
import com.personal.jobhive.helpers.Helper;
import com.personal.jobhive.services.JobService;
import com.personal.jobhive.services.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChartController {

    @Autowired
    JobService jobService;

    @Autowired
    private UserService userService;

    @GetMapping("/api/chart-data")
    public ChartData getChartData(Authentication authentication, HttpSession session) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        List<Job> jobs = jobService.getAll(user.getUserId());
        int[] data = Helper.getChartData(jobs);
        // Return the chart data
        return new ChartData(data, Helper.categories);
    }
}