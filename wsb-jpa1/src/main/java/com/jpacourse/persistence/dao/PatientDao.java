package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long>{
    void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitTime, String description);

    List<PatientEntity> findPatientsByLastName(String lastName); //1
    List<PatientEntity> findPatientsByNumberOfVisitsMoreThan(Long numberOfVisits); //3
    List<PatientEntity> findPatientsByInsuranceAndBirthDateBetween(boolean isInsured, LocalDate startDate, LocalDate endDate); //4
}
