package com.shanavas.jobportal.repository;

import com.shanavas.jobportal.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// JpaRepository<Entity, ID type> gives us save(), findById(), findAll(), deleteById() for free.
public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Optional<Employer> findByEmail(String email);
}
