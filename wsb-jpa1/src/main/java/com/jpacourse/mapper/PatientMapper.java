package com.jpacourse.mapper;

import com.jpacourse.dto.PatientDto;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public final class PatientMapper {

    public PatientDto toDto(PatientEntity patientEntity) {
        PatientDto patientDTO = new PatientDto();
        patientDTO.setId(patientEntity.getId());
        patientDTO.setFirstName(patientEntity.getFirstName());
        patientDTO.setLastName(patientEntity.getLastName());
        patientDTO.setTelephoneNumber(patientEntity.getTelephoneNumber());
        patientDTO.setEmail(patientEntity.getEmail());
        patientDTO.setPatientNumber(patientEntity.getPatientNumber());
        patientDTO.setDateOfBirth(patientEntity.getDateOfBirth());

        patientDTO.setVisits(patientEntity.getVisits().stream()
                .map(this::toVisitDto)
                .collect(Collectors.toList()));

        return patientDTO;
    }

    private PatientDto.VisitDto toVisitDto(VisitEntity visitEntity) {
        PatientDto.VisitDto visitDTO = new PatientDto.VisitDto();
        visitDTO.setVisitTime(visitEntity.getTime());
        visitDTO.setDoctorName(visitEntity.getDoctor().getFirstName() + " " + visitEntity.getDoctor().getLastName());

        visitDTO.setTreatmentTypes(visitEntity.getTreatments().stream()
                .map(treatment -> treatment.getType().toString())
                .collect(Collectors.toList()));

        return visitDTO;
    }
}
