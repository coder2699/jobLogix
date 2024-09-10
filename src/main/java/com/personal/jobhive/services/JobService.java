package com.personal.jobhive.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.personal.jobhive.entities.User;
import com.personal.jobhive.entities.Job;

public interface JobService {
    // save jobs
    Job save(Job job);

    // update job
    Job update(Job job);

    // get jobs
    List<Job> getAll();

    // get job by id

    Job getById(String id);

    // delete job

    void delete(String id);

    // search job
    List<Job> search(String name, String email, String phoneNumber);

    // get jobs by userId
    List<Job> getByUserId(String userId);
    
    Page<Job> getByUser(User user, int page, int size, String sortField, String sortDirection);
}