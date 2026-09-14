package com.shanavas.jobportal.repository;

import com.shanavas.jobportal.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    // Spring Data JPA auto-generates the SQL from the method name itself -
    // this becomes "SELECT * FROM job_postings WHERE location = ?"
    List<JobPosting> findByLocationIgnoreCase(String location);

    // Generates "SELECT * FROM job_postings WHERE title LIKE %?%"
    List<JobPosting> findByTitleContainingIgnoreCase(String keyword);

    List<JobPosting> findByEmployerId(Long employerId);
}
