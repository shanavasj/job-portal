package com.shanavas.jobportal.service;

import com.shanavas.jobportal.entity.Applicant;
import com.shanavas.jobportal.exception.ResourceNotFoundException;
import com.shanavas.jobportal.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    @Autowired
    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    public Applicant registerApplicant(Applicant applicant) {
        return applicantRepository.save(applicant);
    }

    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    public Applicant getApplicantById(Long id) {
        return applicantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Applicant not found with id: " + id));
    }

    public Applicant updateApplicant(Long id, Applicant updatedApplicant) {
        Applicant existing = getApplicantById(id);
        existing.setName(updatedApplicant.getName());
        existing.setPhone(updatedApplicant.getPhone());
        existing.setResumeLink(updatedApplicant.getResumeLink());
        return applicantRepository.save(existing);
    }

    public void deleteApplicant(Long id) {
        Applicant existing = getApplicantById(id);
        applicantRepository.delete(existing);
    }
}
