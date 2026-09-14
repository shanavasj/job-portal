package com.shanavas.jobportal.service;

import com.shanavas.jobportal.entity.Employer;
import com.shanavas.jobportal.exception.ResourceNotFoundException;
import com.shanavas.jobportal.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployerService {

    private final EmployerRepository employerRepository;

    // Constructor injection (preferred over @Autowired on fields - easier to test,
    // makes dependencies explicit).
    @Autowired
    public EmployerService(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    public Employer registerEmployer(Employer employer) {
        return employerRepository.save(employer);
    }

    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

    public Employer getEmployerById(Long id) {
        return employerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employer not found with id: " + id));
    }

    public Employer updateEmployer(Long id, Employer updatedEmployer) {
        Employer existing = getEmployerById(id);
        existing.setCompanyName(updatedEmployer.getCompanyName());
        existing.setContactNumber(updatedEmployer.getContactNumber());
        return employerRepository.save(existing);
    }

    public void deleteEmployer(Long id) {
        Employer existing = getEmployerById(id);
        employerRepository.delete(existing);
    }
}
