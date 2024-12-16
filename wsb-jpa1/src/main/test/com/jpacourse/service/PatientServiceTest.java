package com.jpacourse.service;

import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.repository.AddressRepository;
import com.jpacourse.repository.DoctorRepository;
import com.jpacourse.repository.PatientRepository;
import com.jpacourse.repository.VisitRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jpacourse.persistence.enums.Specialization;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private AddressRepository addressRepository;


    @Test
    public void shouldRemoveVisitWhenPatientRemoved() {
        VisitEntity savedVisit = createTestVisit();
        Long visitId = savedVisit.getId();
        Long patientId = savedVisit.getPatient().getId();

        patientRepository.delete(savedVisit.getPatient());

        Optional<PatientEntity> patientEntity = patientRepository.findById(patientId);
        assertTrue(patientEntity.isEmpty(), "Patient is removed");

        Optional<VisitEntity> visitEntity = visitRepository.findById(visitId);
        assertTrue(visitEntity.isEmpty(), "Visit is removed");

    }

    @Transactional
    protected VisitEntity createTestVisit() {
        AddressEntity address = new AddressEntity();
        address.setCity("Wroclaw");
        address.setPostalCode("50-001");
        address.setAddressLine1("Testowa 2");
        address.setAddressLine2("B");

        AddressEntity savedAddress = addressRepository.save(address);

        AddressEntity address2 = new AddressEntity();
        address2.setCity("Wroclaw");
        address2.setPostalCode("50-001");
        address2.setAddressLine1("Testowa 2");
        address2.setAddressLine2("B");

        AddressEntity savedAddress2 = addressRepository.save(address2);

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Igor");
        patient.setLastName("Karlik");
        patient.setTelephoneNumber("123-123-123");
        patient.setPatientNumber("1");
        patient.setIsInsured(false);
        patient.setDateOfBirth(LocalDate.of(2000, 6, 29));
        patient.setAddress(savedAddress);
        patient.setVisits(new ArrayList<>());

        PatientEntity savedPatient = patientRepository.save(patient);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Anna");
        doctor.setLastName("Smith");
        doctor.setTelephoneNumber("987654321");
        doctor.setEmail("anna.smith@doctor.com");
        doctor.setDoctorNumber("D12345");
        doctor.setAddress(savedAddress2);
        doctor.setSpecialization(Specialization.OCULIST);

        DoctorEntity savedDoctor = doctorRepository.save(doctor);

        VisitEntity visit = new VisitEntity();
        visit.setDescription("Routine Checkup");
        visit.setTime(LocalDateTime.now());
        visit.setPatient(patient);
        visit.setDoctor(savedDoctor);

        savedPatient.getVisits().add(visit);

        visitRepository.save(visit);

        return visit;
    }

}
