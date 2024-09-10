package com.personal.jobhive.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

import com.personal.jobhive.entities.Job;
import com.personal.jobhive.entities.User;
import com.personal.jobhive.forms.JobForm;
import com.personal.jobhive.helpers.AppConstants;
import com.personal.jobhive.helpers.Helper;
import com.personal.jobhive.helpers.Message;
import com.personal.jobhive.helpers.MessageType;
import com.personal.jobhive.services.ImageService;
import com.personal.jobhive.services.JobService;
import com.personal.jobhive.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/jobs")
public class JobController {
    private Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    // add new job
    @RequestMapping("/add")
    public String addJob(Model model) {
        JobForm jobForm = new JobForm();
        jobForm.setStarred(true);
        model.addAttribute("jobForm", jobForm);
        return "user/add_job";
    }

@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute JobForm jobForm, BindingResult result,
            Authentication authentication, HttpSession session) {

        // process the form data

        // 1 validate form

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> logger.info(error.toString()));
            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_job";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        // form ---> contact
        User user = userService.getUserByEmail(username);

        // 2 process the contact picture

        // image process

        // uplod feature code

        String filename = UUID.randomUUID().toString();

        String fileURL = imageService.uploadImage(jobForm.getCompanyImage(), filename);

        Job job = new Job();
        job.setCompany(jobForm.getCompany());
        job.setStarred(jobForm.isStarred());
        job.setJobRole(jobForm.getJobRole());
        job.setLocation(jobForm.getLocation());
        job.setDescription(jobForm.getDescription());
        job.setJobLink(jobForm.getJobLink());
        job.setCvLink(jobForm.getCvLink());
        job.setPlatform(jobForm.getPlatform());
        job.setUser(user);
        job.setAppliedDate(jobForm.getAppliedDate());
        // job.setInterviewDate(jobForm.getInterviewDate());
        job.setPicture(fileURL);
        job.setCloudinaryImagePublicId(filename);
        jobService.save(job);
        System.out.println(jobForm);

        // 3 set the contact picture url

        // 4 `set message to be displayed on the view

        session.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new job")
                        .type(MessageType.green)
                        .build());

        return "redirect:/user/jobs/add";
    }

    // view applied jobs
    @RequestMapping
    public String viewJobs(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "company") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,
            Authentication authentication) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        Page<Job> pageContact = jobService.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/jobs";
    }
}
