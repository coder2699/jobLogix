package com.personal.jobhive.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

import com.personal.jobhive.entities.Job;
import com.personal.jobhive.entities.User;
import com.personal.jobhive.forms.JobForm;
import com.personal.jobhive.forms.JobSearchForm;
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
    public String saveJob(@Valid @ModelAttribute JobForm jobForm, BindingResult result,
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
        User user = userService.getUserByEmail(username);
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

        // 3 set the job picture url

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
        Page<Job> pageJob = jobService.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageJob", pageJob);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("jobSearchForm", new JobSearchForm());
        return "user/jobs";
    }


    // search handler

    @RequestMapping("/search")
    public String searchHandler(

            @ModelAttribute JobSearchForm jobSearchForm,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "company") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

        logger.info("field {} keyword {}", jobSearchForm.getField(), jobSearchForm.getValue());

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Job> pageJob = null;
        if (jobSearchForm.getField().equalsIgnoreCase("company")) {
            pageJob = jobService.searchByCompany(jobSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (jobSearchForm.getField().equalsIgnoreCase("jobRole")) {
            pageJob = jobService.searchByJobRole(jobSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (jobSearchForm.getField().equalsIgnoreCase("location")) {
            pageJob = jobService.searchByLocation(jobSearchForm.getValue(), size, page, sortBy,
                    direction, user);
        }

        logger.info("pageJob {}", pageJob);

        model.addAttribute("jobSearchForm", jobSearchForm);

        model.addAttribute("pageJob", pageJob);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/search";
    }

    // detete
    @RequestMapping("/delete/{jobId}")
    public String deleteJob(@PathVariable("jobId") String jobId, HttpSession session) {
        jobService.delete(jobId);
        logger.info("jobId {} deleted", jobId);

        session.setAttribute("message",
                Message.builder()
                        .content("Job Application is Deleted successfully !! ")
                        .type(MessageType.green)
                        .build());
        return "redirect:/user/jobs";
    }

    // update job form view
    @GetMapping("/view/{jobId}")
    public String updateJobFormView(@PathVariable("jobId") String jobId, Model model) {
        var job = jobService.getById(jobId);
        JobForm jobForm = new JobForm();
        jobForm.setCompany(job.getCompany());
        jobForm.setStarred(job.isStarred());
        jobForm.setJobRole(job.getJobRole());
        jobForm.setLocation(job.getLocation());
        jobForm.setDescription(job.getDescription());
        jobForm.setJobLink(job.getJobLink());
        jobForm.setCvLink(job.getCvLink());
        jobForm.setPlatform(job.getPlatform());
        jobForm.setAppliedDate(job.getAppliedDate());

        model.addAttribute("jobForm", jobForm);
        model.addAttribute("jobId", jobId);

        return "user/update_job_view";
    }

    @RequestMapping(value = "/update/{jobId}", method = RequestMethod.POST)
    public String updateJob(@PathVariable("jobId") String jobId,
            @Valid @ModelAttribute JobForm jobForm,
            BindingResult bindingResult,
            Model model) {

        // update the job
        if (bindingResult.hasErrors()) {
            return "user/update_job_view";
        }

        var con = jobService.getById(jobId);
        con.setJobId(jobId);
        con.setCompany(jobForm.getCompany());
        con.setStarred(jobForm.isStarred());
        con.setJobRole(jobForm.getJobRole());
        con.setLocation(jobForm.getLocation());
        con.setDescription(jobForm.getDescription());
        con.setJobLink(jobForm.getJobLink());
        con.setCvLink(jobForm.getCvLink());
        con.setPlatform(jobForm.getPlatform());

        var updateCon = jobService.update(con);
        logger.info("updated job {}", updateCon);

        model.addAttribute("message", Message.builder().content("Jobs Updated !!").type(MessageType.green).build());

        return "redirect:/user/jobs/view/" + jobId;
    }
}
