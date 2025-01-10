package com.jpacourse.persistance.dao;

import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.dao.VisitDao;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private VisitDao visitDao;
    @Autowired
    private AddressDao addressDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void shouldAddVisitToPatient() {
        AddressEntity patientAddress = new AddressEntity();
        patientAddress.setCity("Wroclaw");
        patientAddress.setPostalCode("50-001");
        patientAddress.setAddressLine1("Testowa 2");
        patientAddress.setAddressLine2("B");
        addressDao.save(patientAddress);

        AddressEntity doctorAddress = new AddressEntity();
        doctorAddress.setCity("Wroclaw");
        doctorAddress.setPostalCode("50-001");
        doctorAddress.setAddressLine1("Testowa 3");
        doctorAddress.setAddressLine2("C");
        addressDao.save(doctorAddress);

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Igor");
        patient.setLastName("Karlik");
        patient.setTelephoneNumber("123-123-123");
        patient.setPatientNumber("1");
        patient.setIsInsured(false);
        patient.setDateOfBirth(LocalDate.of(2000, 6, 29));
        patient.setAddress(patientAddress);
        patientDao.save(patient);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Anna");
        doctor.setLastName("Smith");
        doctor.setTelephoneNumber("987654321");
        doctor.setEmail("anna.smith@doctor.com");
        doctor.setDoctorNumber("D12345");
        doctor.setAddress(doctorAddress);
        doctor.setSpecialization(Specialization.OCULIST);
        doctorDao.save(doctor);

        LocalDateTime visitDate = LocalDateTime.now().plusDays(2);
        String description = "Routine Checkup";
        patientDao.addVisitToPatient(patient.getId(), doctor.getId(), visitDate, description);

        PatientEntity updatedPatient = patientDao.findOne(patient.getId());
        assertThat(updatedPatient).isNotNull();
        assertThat(updatedPatient.getVisits()).hasSize(1);

        VisitEntity visit = updatedPatient.getVisits().get(0);
        assertThat(visit.getTime()).isEqualTo(visitDate);
        assertThat(visit.getDescription()).isEqualTo(description);
        assertThat(visit.getPatient().getId()).isEqualTo(patient.getId());
        assertThat(visit.getDoctor().getId()).isEqualTo(doctor.getId());

        VisitEntity savedVisit = visitDao.findOne(visit.getId());
        assertThat(savedVisit).isNotNull();
        assertThat(savedVisit.getDescription()).isEqualTo(description);
    }

    @Test
    @Transactional
    public void shouldFindPatientsByLastName() {
        AddressEntity address = new AddressEntity();
        address.setCity("Wroclaw");
        address.setPostalCode("50-001");
        address.setAddressLine1("Zmigrodzka 32");
        address.setAddressLine2("B");
        addressDao.save(address);

        PatientEntity patient1 = new PatientEntity();
        patient1.setFirstName("Igor");
        patient1.setLastName("Karlik");
        patient1.setTelephoneNumber("123-123-123");
        patient1.setPatientNumber("1");
        patient1.setIsInsured(false);
        patient1.setDateOfBirth(LocalDate.of(2000, 6, 29));
        patient1.setAddress(address);
        patientDao.save(patient1);

        PatientEntity patient2 = new PatientEntity();
        patient2.setFirstName("Anna");
        patient2.setLastName("Karlik");
        patient2.setTelephoneNumber("321-321-321");
        patient2.setPatientNumber("2");
        patient2.setIsInsured(true);
        patient2.setDateOfBirth(LocalDate.of(1990, 4, 15));
        patient2.setAddress(address);
        patientDao.save(patient2);

        List<PatientEntity> patients = patientDao.findPatientsByLastName("Karlik");

        assertThat(patients).hasSize(2);
        assertThat(patients).extracting(PatientEntity::getLastName).containsOnly("Karlik");
    }

    @Test
    @Transactional
    public void shouldFindPatientsByNumberOfVisitsMoreThan() {
        AddressEntity address = new AddressEntity();
        address.setCity("Wroclaw");
        address.setPostalCode("50-001");
        address.setAddressLine1("Zmigrodzka 32");
        address.setAddressLine2("B");
        addressDao.save(address);

        PatientEntity patient1 = new PatientEntity();
        patient1.setFirstName("Igor");
        patient1.setLastName("Karlik");
        patient1.setTelephoneNumber("123-123-123");
        patient1.setPatientNumber("1");
        patient1.setIsInsured(false);
        patient1.setDateOfBirth(LocalDate.of(2000, 6, 29));
        patient1.setAddress(address);
        patientDao.save(patient1);

        PatientEntity patient2 = new PatientEntity();
        patient2.setFirstName("Bob");
        patient2.setLastName("Smith");
        patient2.setTelephoneNumber("321-321-321");
        patient2.setPatientNumber("2");
        patient2.setIsInsured(true);
        patient2.setDateOfBirth(LocalDate.of(1990, 4, 15));
        patient2.setAddress(address);
        patientDao.save(patient2);

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
        patientDao.addVisitToPatient(patient1.getId(), doctor.getId(), visitDate1, description1);

        LocalDateTime visitDate2 = LocalDateTime.now().plusDays(3);
        String description2 = "Second Checkup";
        patientDao.addVisitToPatient(patient1.getId(), doctor.getId(), visitDate2, description2);

        List<PatientEntity> patients = patientDao.findPatientsByNumberOfVisitsMoreThan(1L);

        assertThat(patients).hasSize(1);
        assertThat(patients.get(0).getLastName()).isEqualTo("Karlik");
        assertThat(patients.get(0).getVisits()).hasSizeGreaterThan(1);
    }

    @Test
    @Transactional
    public void shouldFindPatientsByInsuranceAndBirthDateBetween() {
        AddressEntity address = new AddressEntity();
        address.setCity("Wroclaw");
        address.setPostalCode("50-001");
        address.setAddressLine1("Zmigrodzka 32");
        address.setAddressLine2("B");
        addressDao.save(address);

        PatientEntity patient1 = new PatientEntity();
        patient1.setFirstName("Igor");
        patient1.setLastName("Karlik");
        patient1.setTelephoneNumber("123-123-123");
        patient1.setPatientNumber("1");
        patient1.setIsInsured(false);
        patient1.setDateOfBirth(LocalDate.of(2000, 6, 29));
        patient1.setAddress(address);
        patientDao.save(patient1);

        PatientEntity patient2 = new PatientEntity();
        patient2.setFirstName("Bob");
        patient2.setLastName("Smith");
        patient2.setTelephoneNumber("321-321-321");
        patient2.setPatientNumber("2");
        patient2.setIsInsured(true);
        patient2.setDateOfBirth(LocalDate.of(1990, 4, 15));
        patient2.setAddress(address);
        patientDao.save(patient2);

        LocalDate startDate = LocalDate.of(1980, 4, 15);
        LocalDate endDate = LocalDate.of(1995, 4, 15);

        List<PatientEntity> patients = patientDao.findPatientsByInsuranceAndBirthDateBetween(true, startDate, endDate);

        assertThat(patients).hasSize(1);
        assertThat(patients.get(0).getLastName()).isEqualTo("Smith");
    }

    @Test
    @Transactional
    public void shouldThrowOptimisticLockExceptionOnConcurrentModification() throws InterruptedException {
        final Logger logger = LoggerFactory.getLogger(PatientDaoTest.class);

        AddressEntity address = new AddressEntity();
        address.setCity("Wroclaw");
        address.setPostalCode("50-001");
        address.setAddressLine1("Zmigrodzka 32");
        address.setAddressLine2("B");

        entityManager.persist(address);

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Igor");
        patient.setLastName("Karlik");
        patient.setTelephoneNumber("123-123-123");
        patient.setPatientNumber("1");
        patient.setIsInsured(false);
        patient.setDateOfBirth(LocalDate.of(2000, 6, 29));
        patient.setAddress(address);

        entityManager.persist(patient);

        entityManager.flush();
        entityManager.clear();

        Runnable task1 = () -> {
            try{
                entityManager.getTransaction().begin();
                PatientEntity patient1 = entityManager.find(PatientEntity.class, patient.getId());
                logger.info("Thread 1 version: " + patient1.getVersion());
                patient1.setFirstName("Igor");
                entityManager.merge(patient1);
                entityManager.flush();
                entityManager.getTransaction().commit();
                logger.info("Thread 1 updated version: " + patient1.getVersion());
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                logger.error("Thread 1 exception:", e);
            }
        };

        Runnable task2 = () -> {
            try{
                entityManager.getTransaction().begin();
                PatientEntity patient2 = entityManager.find(PatientEntity.class, patient.getId());
                logger.info("Thread 2 version: " + patient2.getVersion());
                patient2.setFirstName("Igor");
                entityManager.merge(patient2);
                entityManager.flush();
                entityManager.getTransaction().commit();
                logger.info("Thread 2 updated version: " + patient2.getVersion());
            } catch (OptimisticLockException e) {
                entityManager.getTransaction().rollback();
                logger.error("Thread 2 OptimisticLockException", e);
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                logger.error("Thread 2 exception:", e);
            }
        };

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
