package com.jpacourse.repository;

import com.jpacourse.persistence.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    @Override
    Optional<PatientEntity> findById(Long id);
}
