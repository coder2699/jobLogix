package com.personal.jobhive.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.jobhive.entities.Job;
import com.personal.jobhive.services.JobService;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    JobService jobService;

    @GetMapping("/jobs/{jobId}")
    public Job getJob(@PathVariable String jobId) {
        return jobService.getById(jobId);
    }
}
