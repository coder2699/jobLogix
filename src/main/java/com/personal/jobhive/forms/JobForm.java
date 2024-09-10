package com.personal.jobhive.forms;

import java.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;
import com.personal.jobhive.entities.JobStatus;
import com.personal.jobhive.validators.ValidFile;
import jakarta.validation.constraints.NotBlank;
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

    private String description;
    private String jobLink;
    private String platform;

    private boolean starred;
    private String cvLink;

    private LocalDate appliedDate;
    private LocalDate interviewDate;
    
    private JobStatus status = JobStatus.Apply;
    private MultipartFile picture;

    @ValidFile(message = "Invalid File")
    private MultipartFile companyImage;
}
