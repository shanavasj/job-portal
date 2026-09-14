package com.shanavas.jobportal.controller;

import com.shanavas.jobportal.entity.ApplicationStatus;
import com.shanavas.jobportal.entity.JobApplication;
import com.shanavas.jobportal.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @Autowired
    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    // 6) CREATE - POST /api/applications?jobId=1&applicantId=2
    @PostMapping
    public ResponseEntity<JobApplication> apply(@RequestParam Long jobId, @RequestParam Long applicantId) {
        JobApplication application = jobApplicationService.applyToJob(jobId, applicantId);
        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }

    // 7) READ ONE - GET /api/applications/{id}
    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getById(@PathVariable Long id) {
        return ResponseEntity.ok(jobApplicationService.getApplicationById(id));
    }

    // 8) READ - GET /api/applications/applicant/{applicantId}  ("my applications")
    @GetMapping("/applicant/{applicantId}")
    public ResponseEntity<List<JobApplication>> getByApplicant(@PathVariable Long applicantId) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByApplicant(applicantId));
    }

    // Bonus - GET /api/applications/job/{jobId}  (employer sees who applied)
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<JobApplication>> getByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByJob(jobId));
    }

    // Bonus - PUT /api/applications/{id}/status?status=SHORTLISTED
    @PutMapping("/{id}/status")
    public ResponseEntity<JobApplication> updateStatus(@PathVariable Long id, @RequestParam ApplicationStatus status) {
        return ResponseEntity.ok(jobApplicationService.updateStatus(id, status));
    }

    // Bonus - DELETE /api/applications/{id}  (withdraw)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> withdraw(@PathVariable Long id) {
        jobApplicationService.withdrawApplication(id);
        return ResponseEntity.noContent().build();
    }
}
