package com.personal.jobhive.services.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.personal.jobhive.entities.Job;
import com.personal.jobhive.entities.User;
import com.personal.jobhive.helpers.ResourceNotFoundException;
import com.personal.jobhive.repositories.JobRepo;
import com.personal.jobhive.services.JobService;

@Service
public class JobServiceImpl implements JobService

{

    @Autowired
    private JobRepo jobsRepo;

    @Override
    public Job save(Job job) {
        String jobId = UUID.randomUUID().toString();
        job.setJobId(jobId);
        return jobsRepo.save(job);
    }

    @Override
    public Job update(Job job) {
        var jobOld = jobsRepo.findById(job.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        jobOld.setCompany(job.getCompany());
        jobOld.setStarred(job.isStarred());
        jobOld.setReferred(job.isReferred());
        jobOld.setReferredBy(job.getReferredBy());
        jobOld.setJobRole(job.getJobRole());
        jobOld.setLocation(job.getLocation());
        jobOld.setDescription(job.getDescription());
        jobOld.setJobLink(job.getJobLink());
        jobOld.setCvLink(job.getCvLink());
        jobOld.setPlatform(job.getPlatform());
        jobOld.setAppliedDate(job.getAppliedDate());
        jobOld.setCloudinaryImagePublicId(job.getCloudinaryImagePublicId());

        return jobsRepo.save(jobOld);
    }

    @Override
    public List<Job> getAll() {
        return jobsRepo.findAll();
    }

    @Override
    public Job getById(String id) {
        return jobsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with given id " + id));
    }

    @Override
    public void delete(String id) {
        var job = jobsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with given id " + id));
        jobsRepo.delete(job);

    }

    @Override
    public List<Job> getByUserId(String userId) {
        return jobsRepo.findByUserId(userId);
    }

    @Override
    public Page<Job> getByUser(User user, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort);

        return jobsRepo.findByUser(user, pageable);

    }

    @Override
    public Page<Job> searchByCompany(String companyKeyword, int size, int page, String sortBy, String order, User user) {

        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return jobsRepo.findByUserAndCompanyContaining(user, companyKeyword, pageable);
    }

    @Override
    public Page<Job> searchByJobRole(String roleKeyword, int size, int page, String sortBy, String order,
            User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return jobsRepo.findByUserAndJobRoleContaining(user, roleKeyword, pageable);
    }

    @Override
    public Page<Job> searchByLocation(String locationKeyword, int size, int page, String sortBy,
            String order, User user) {

        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return jobsRepo.findByUserAndLocationContaining(user, locationKeyword, pageable);
    }

    @Override
    public List<Job> getStarredJobs() {
        return jobsRepo.findAll().stream()
        .filter(job -> job.isStarred())
        .collect(Collectors.toList());
    }
}