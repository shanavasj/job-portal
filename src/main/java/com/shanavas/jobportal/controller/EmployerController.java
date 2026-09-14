package com.shanavas.jobportal.controller;

import com.shanavas.jobportal.entity.Employer;
import com.shanavas.jobportal.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employers")
public class EmployerController {

    private final EmployerService employerService;

    @Autowired
    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @PostMapping
    public ResponseEntity<Employer> register(@RequestBody Employer employer) {
        return new ResponseEntity<>(employerService.registerEmployer(employer), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Employer>> getAll() {
        return ResponseEntity.ok(employerService.getAllEmployers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employer> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employerService.getEmployerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employer> update(@PathVariable Long id, @RequestBody Employer employer) {
        return ResponseEntity.ok(employerService.updateEmployer(id, employer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employerService.deleteEmployer(id);
        return ResponseEntity.noContent().build();
    }
}
