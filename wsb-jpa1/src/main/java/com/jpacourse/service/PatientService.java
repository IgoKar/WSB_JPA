package com.jpacourse.service;

import com.jpacourse.dto.PatientDto;

import java.util.Optional;

public interface PatientService {
    Optional<PatientDto> getPatientById(Long id);
}
