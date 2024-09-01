package com.personal.jobhive.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Job {
    @Id
    private String jobId;
    private String company;
    private String description;
    private String jobLink;
    private String platform;
    private String cvLink;
    private LocalDate appliedDate;
    private LocalDate interviewDate;
    private JobStatus status = JobStatus.Applied;
    private boolean starred = false;
    
    @ManyToOne
    private User user;
}
