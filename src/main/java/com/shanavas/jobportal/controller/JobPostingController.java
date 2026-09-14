package com.shanavas.jobportal.controller;

import com.shanavas.jobportal.entity.JobPosting;
import com.shanavas.jobportal.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// @RestController = @Controller + @ResponseBody: every method return value is
// serialized straight to JSON, which is what makes this a REST API rather than
// a page-returning MVC controller.
@RestController
@RequestMapping("/api/jobs")
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @Autowired
    public JobPostingController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    // 1) CREATE - POST /api/jobs
    @PostMapping
    public ResponseEntity<JobPosting> createJob(@RequestBody JobPosting jobPosting) {
        JobPosting created = jobPostingService.createJob(jobPosting);
        return new ResponseEntity<>(created, HttpStatus.CREATED); // 201
    }

    // 2) READ ALL - GET /api/jobs  (supports optional ?location= and ?keyword= filters)
    @GetMapping
    public ResponseEntity<List<JobPosting>> getAllJobs(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String keyword) {

        List<JobPosting> jobs;
        if (keyword != null && !keyword.isBlank()) {
            jobs = jobPostingService.searchByTitle(keyword);
        } else if (location != null && !location.isBlank()) {
            jobs = jobPostingService.getJobsByLocation(location);
        } else {
            jobs = jobPostingService.getAllJobs();
        }
        return ResponseEntity.ok(jobs); // 200
    }

    // 3) READ ONE - GET /api/jobs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<JobPosting> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobPostingService.getJobById(id));
    }

    // 4) UPDATE - PUT /api/jobs/{id}
    @PutMapping("/{id}")
    public ResponseEntity<JobPosting> updateJob(@PathVariable Long id, @RequestBody JobPosting jobPosting) {
        return ResponseEntity.ok(jobPostingService.updateJob(id, jobPosting));
    }

    // 5) DELETE - DELETE /api/jobs/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobPostingService.deleteJob(id);
        return ResponseEntity.noContent().build(); // 204
    }

    // Bonus - jobs posted by a specific employer (used by the employer dashboard view)
    @GetMapping("/employer/{employerId}")
    public ResponseEntity<List<JobPosting>> getJobsByEmployer(@PathVariable Long employerId) {
        return ResponseEntity.ok(jobPostingService.getJobsByEmployer(employerId));
    }
}
