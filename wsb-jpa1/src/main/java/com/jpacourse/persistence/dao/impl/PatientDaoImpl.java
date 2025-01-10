package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitTime, String description) {

        PatientEntity patient = findOne(patientId);

        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);

        VisitEntity visit = new VisitEntity();
        visit.setDescription(description);
        visit.setTime(visitTime);
        visit.setPatient(patient);
        visit.setDoctor(doctor);

        patient.getVisits().add(visit);

        update(patient);
    }

    @Override
    public List<PatientEntity> findPatientsByLastName(String lastName) {
        return entityManager.createQuery("SELECT p FROM PatientEntity p WHERE p.lastName = :lastName", PatientEntity.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsByNumberOfVisitsMoreThan(Long numOfVisits) {
        return entityManager.createQuery("SELECT p FROM PatientEntity p WHERE (SELECT COUNT(v) FROM p.visits v) > :numOfVisits ", PatientEntity.class)
                .setParameter("numOfVisits", numOfVisits)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsByInsuranceAndBirthDateBetween(boolean isInsured, LocalDate startDate, LocalDate endDate) {
        return entityManager.createQuery("SELECT p FROM PatientEntity p WHERE p.isInsured = :isInsured AND p.dateOfBirth BETWEEN :startDate AND :endDate", PatientEntity.class)
                .setParameter("isInsured", isInsured)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}
