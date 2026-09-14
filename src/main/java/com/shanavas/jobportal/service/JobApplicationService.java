package com.shanavas.jobportal.service;

import com.shanavas.jobportal.entity.Applicant;
import com.shanavas.jobportal.entity.ApplicationStatus;
import com.shanavas.jobportal.entity.JobApplication;
import com.shanavas.jobportal.entity.JobPosting;
import com.shanavas.jobportal.exception.ResourceNotFoundException;
import com.shanavas.jobportal.repository.ApplicantRepository;
import com.shanavas.jobportal.repository.JobApplicationRepository;
import com.shanavas.jobportal.repository.JobPostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobPostingRepository jobPostingRepository;
    private final ApplicantRepository applicantRepository;

    @Autowired
    public JobApplicationService(JobApplicationRepository jobApplicationRepository,
                                  JobPostingRepository jobPostingRepository,
                                  ApplicantRepository applicantRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.applicantRepository = applicantRepository;
    }

    // ---- CREATE: applicant applies to a job ----
    public JobApplication applyToJob(Long jobId, Long applicantId) {
        JobPosting job = jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job posting not found with id: " + jobId));

        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new ResourceNotFoundException("Applicant not found with id: " + applicantId));

        // Prevent duplicate applications to the same job.
        jobApplicationRepository.findByJobPostingIdAndApplicantId(jobId, applicantId)
                .ifPresent(a -> { throw new IllegalStateException("Applicant has already applied to this job"); });

        JobApplication application = new JobApplication(job, applicant);
        return jobApplicationRepository.save(application);
    }

    // ---- READ (by id) ----
    public JobApplication getApplicationById(Long id) {
        return jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
    }

    // ---- READ (all applications by an applicant - "my applications" view) ----
    public List<JobApplication> getApplicationsByApplicant(Long applicantId) {
        return jobApplicationRepository.findByApplicantId(applicantId);
    }

    // ---- READ (all applicants for a given job - employer's view) ----
    public List<JobApplication> getApplicationsByJob(Long jobId) {
        return jobApplicationRepository.findByJobPostingId(jobId);
    }

    // ---- UPDATE (employer changes status: SHORTLISTED / REJECTED / HIRED) ----
    public JobApplication updateStatus(Long applicationId, ApplicationStatus status) {
        JobApplication application = getApplicationById(applicationId);
        application.setStatus(status);
        return jobApplicationRepository.save(application);
    }

    // ---- DELETE (applicant withdraws application) ----
    public void withdrawApplication(Long id) {
        JobApplication application = getApplicationById(id);
        jobApplicationRepository.delete(application);
    }
}
