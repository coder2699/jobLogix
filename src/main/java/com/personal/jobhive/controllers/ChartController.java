package com.personal.jobhive.controllers;
import com.personal.jobhive.entities.ChartData;
import com.personal.jobhive.entities.Job;
import com.personal.jobhive.helpers.Helper;
import com.personal.jobhive.services.JobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChartController {

    @Autowired
    JobService jobService;

    @GetMapping("/api/chart-data")
    public ChartData getChartData() {
        List<Job> jobs = jobService.getAll();
        int[] data = Helper.getChartData(jobs);
        // Return the chart data
        return new ChartData(data, Helper.categories);
    }
}