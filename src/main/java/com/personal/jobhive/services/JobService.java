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

    // get jobs by userId
    List<Job> getByUserId(String userId);
    
    Page<Job> getByUser(User user, int page, int size, String sortField, String sortDirection);
    
    Page<Job> searchByCompany(String companyKeyword, int size, int page, String sortBy, String order, User user);

    Page<Job> searchByJobRole(String roleKeyword, int size, int page, String sortBy, String order, User user);

    Page<Job> searchByLocation(String locationKeyword, int size, int page, String sortBy, String order, User user);
}