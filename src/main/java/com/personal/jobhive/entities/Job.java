package com.personal.jobhive.entities;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    private String jobId;
    private String company;
    private String jobRole;
    private String location;
    @Column(length = 50000)
    private String description;
    private String jobLink;
    private String platform;
    private String cvLink;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate appliedDate;
    private LocalDate interviewDate;
    private JobStatus status = JobStatus.Apply;
    private boolean starred = false;
    private boolean isReferred = false;
    private String referredBy;
    private String picture;
    private String cloudinaryImagePublicId;
    
    @ManyToOne
    @JsonIgnore
    private User user;
}
