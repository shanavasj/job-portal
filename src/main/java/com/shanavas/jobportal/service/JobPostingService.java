package com.shanavas.jobportal.service;

import com.shanavas.jobportal.entity.Category;
import com.shanavas.jobportal.entity.Employer;
import com.shanavas.jobportal.entity.JobPosting;
import com.shanavas.jobportal.exception.ResourceNotFoundException;
import com.shanavas.jobportal.repository.CategoryRepository;
import com.shanavas.jobportal.repository.JobPostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public JobPostingService(JobPostingRepository jobPostingRepository, CategoryRepository categoryRepository) {
        this.jobPostingRepository = jobPostingRepository;
        this.categoryRepository = categoryRepository;
    }

    // ---- CREATE ----
    public JobPosting createJob(JobPosting jobPosting) {
        jobPosting.setPostedDate(LocalDate.now());
        return jobPostingRepository.save(jobPosting);
    }

    // ---- READ (all) ----
    public List<JobPosting> getAllJobs() {
        return jobPostingRepository.findAll();
    }

    // ---- READ (by id) ----
    public JobPosting getJobById(Long id) {
        return jobPostingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job posting not found with id: " + id));
    }

    // ---- READ (search by title keyword) ----
    public List<JobPosting> searchByTitle(String keyword) {
        return jobPostingRepository.findByTitleContainingIgnoreCase(keyword);
    }

    // ---- READ (filter by location) ----
    public List<JobPosting> getJobsByLocation(String location) {
        return jobPostingRepository.findByLocationIgnoreCase(location);
    }

    // ---- READ (jobs posted by a specific employer) ----
    public List<JobPosting> getJobsByEmployer(Long employerId) {
        return jobPostingRepository.findByEmployerId(employerId);
    }

    // ---- UPDATE ----
    public JobPosting updateJob(Long id, JobPosting updatedJob) {
        JobPosting existing = getJobById(id);
        existing.setTitle(updatedJob.getTitle());
        existing.setDescription(updatedJob.getDescription());
        existing.setLocation(updatedJob.getLocation());
        existing.setSalary(updatedJob.getSalary());
        if (updatedJob.getCategory() != null) {
            existing.setCategory(updatedJob.getCategory());
        }
        return jobPostingRepository.save(existing);
    }

    // ---- DELETE ----
    public void deleteJob(Long id) {
        JobPosting existing = getJobById(id);
        jobPostingRepository.delete(existing);
    }

    // ---- Category helper: find-or-create, so callers can pass a category name ----
    public Category resolveCategory(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName)
                .orElseGet(() -> categoryRepository.save(new Category(categoryName)));
    }
}
