package com.personal.jobhive.forms;

import java.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;
import com.personal.jobhive.entities.JobStatus;
import com.personal.jobhive.validators.ValidFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JobForm {
    
    @NotBlank(message = "Company is required")
    private String company;
    @NotBlank(message = "Job Role is required")
    private String jobRole;
    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Job Url is required")
    private String jobLink;
    @NotBlank(message = "Platform is required")
    private String platform;

    private boolean starred;
    private boolean isReferred;
    private String referredBy;
    @NotBlank(message = "CV Link is required")
    private String cvLink;

    @NotNull(message = "Date is required")
    private LocalDate appliedDate;
    private LocalDate interviewDate;
    
    private JobStatus status = JobStatus.Apply;
    private MultipartFile picture;

    private String currentStatus;

    @ValidFile(message = "Invalid File")
    private MultipartFile companyImage;
}
