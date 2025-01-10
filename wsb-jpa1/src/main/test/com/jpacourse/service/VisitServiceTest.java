package com.jpacourse.service;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;
    @Autowired
    private PatientDao patientDao;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private AddressDao addressDao;

    @Test
    @Transactional
    public void shouldFindVisitsByPatientId() {
        AddressEntity address = new AddressEntity();
        address.setCity("Wroclaw");
        address.setPostalCode("50-001");
        address.setAddressLine1("Zmigrodzka 32");
        address.setAddressLine2("B");
        addressDao.save(address);

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Igor");
        patient.setLastName("Karlik");
        patient.setTelephoneNumber("123-123-123");
        patient.setPatientNumber("1");
        patient.setIsInsured(false);
        patient.setDateOfBirth(LocalDate.of(2000, 6, 29));
        patient.setAddress(address);
        patientDao.save(patient);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Mike");
        doctor.setLastName("Ent");
        doctor.setTelephoneNumber("987654321");
        doctor.setEmail("mike.ent@doctor.com");
        doctor.setDoctorNumber("D12345");
        doctor.setAddress(address);
        doctor.setSpecialization(Specialization.OCULIST);
        doctorDao.save(doctor);

        LocalDateTime visitDate1 = LocalDateTime.now().plusDays(2);
        String description1 = "Routine Checkup";
        patientDao.addVisitToPatient(patient.getId(), doctor.getId(), visitDate1, description1);

        LocalDateTime visitDate2 = LocalDateTime.now().plusDays(3);
        String description2 = "Second Checkup";
        patientDao.addVisitToPatient(patient.getId(), doctor.getId(), visitDate2, description2);

        List<VisitTO> visits = visitService.getVisitsByPatientId(patient.getId());

        assertThat(visits).hasSize(2);
        assertThat(visits).extracting(VisitTO::getDescription).containsOnly(description1, description2);
    }
}
