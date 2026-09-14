package com.shanavas.jobportal.repository;

import com.shanavas.jobportal.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByApplicantId(Long applicantId);

    List<JobApplication> findByJobPostingId(Long jobId);

    // Prevents the same applicant applying twice to the same job.
    Optional<JobApplication> findByJobPostingIdAndApplicantId(Long jobId, Long applicantId);
}
