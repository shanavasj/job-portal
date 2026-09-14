package com.shanavas.jobportal.controller;

import com.shanavas.jobportal.entity.Applicant;
import com.shanavas.jobportal.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/applicants")
public class ApplicantController {

    private final ApplicantService applicantService;

    @Autowired
    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping
    public ResponseEntity<Applicant> register(@RequestBody Applicant applicant) {
        return new ResponseEntity<>(applicantService.registerApplicant(applicant), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Applicant>> getAll() {
        return ResponseEntity.ok(applicantService.getAllApplicants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Applicant> getById(@PathVariable Long id) {
        return ResponseEntity.ok(applicantService.getApplicantById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Applicant> update(@PathVariable Long id, @RequestBody Applicant applicant) {
        return ResponseEntity.ok(applicantService.updateApplicant(id, applicant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        applicantService.deleteApplicant(id);
        return ResponseEntity.noContent().build();
    }
}
