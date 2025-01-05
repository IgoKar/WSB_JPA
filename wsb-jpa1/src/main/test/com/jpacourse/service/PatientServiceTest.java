package com.jpacourse.service;

import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.dao.VisitDao;
import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientDao patientDao;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private VisitDao visitDao;
    @Autowired
    private AddressDao addressDao;

    @Test
    @Transactional
    public void shouldRemoveVisitWhenPatientRemoved() {
        VisitEntity savedVisit = createTestVisit();
        Long visitId = savedVisit.getId();
        Long patientId = savedVisit.getPatient().getId();

        patientDao.delete(savedVisit.getPatient());

        Optional<PatientEntity> patientEntity = Optional.ofNullable(patientDao.findOne(patientId));
        assertTrue(patientEntity.isEmpty(), "Patient should be removed from the database");

        Optional<VisitEntity> visitEntity = Optional.ofNullable(visitDao.findOne(visitId));
        assertTrue(visitEntity.isEmpty(), "Visit should be removed when the patient is deleted");
    }

    @Transactional
    protected VisitEntity createTestVisit() {
        AddressEntity address = new AddressEntity();
        address.setCity("Wroclaw");
        address.setPostalCode("50-001");
        address.setAddressLine1("Testowa 2");
        address.setAddressLine2("B");

        AddressEntity savedAddress = addressDao.save(address);

        AddressEntity address2 = new AddressEntity();
        address2.setCity("Wroclaw");
        address2.setPostalCode("50-001");
        address2.setAddressLine1("Testowa 2");
        address2.setAddressLine2("B");

        AddressEntity savedAddress2 = addressDao.save(address2);

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Igor");
        patient.setLastName("Karlik");
        patient.setTelephoneNumber("123-123-123");
        patient.setPatientNumber("1");
        patient.setIsInsured(false);
        patient.setDateOfBirth(LocalDate.of(2000, 6, 29));
        patient.setAddress(savedAddress);
        patient.setVisits(new ArrayList<>());

        PatientEntity savedPatient = patientDao.save(patient);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Anna");
        doctor.setLastName("Smith");
        doctor.setTelephoneNumber("987654321");
        doctor.setEmail("anna.smith@doctor.com");
        doctor.setDoctorNumber("D12345");
        doctor.setAddress(savedAddress2);
        doctor.setSpecialization(Specialization.OCULIST);

        DoctorEntity savedDoctor = doctorDao.save(doctor);

        VisitEntity visit = new VisitEntity();
        visit.setDescription("Routine Checkup");
        visit.setTime(LocalDateTime.now());
        visit.setPatient(savedPatient);
        visit.setDoctor(savedDoctor);

        savedPatient.getVisits().add(visit);

        visitDao.save(visit);

        return visit;
    }
}
