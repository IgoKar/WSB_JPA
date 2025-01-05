package com.jpacourse.controller;

import com.jpacourse.dto.AddressTO;
import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.AddressMapper;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.repository.AddressRepository;
import com.jpacourse.repository.PatientRepository;
import com.jpacourse.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
class PatientServiceTest {

    @Autowired
    private AddressMapper addressMapper;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    private AddressEntity existingAddress;
    private AddressEntity newAddress;
    private AddressTO existingAddressTO;
    private AddressTO newAddressTO;
    private PatientTO patientTOWithExistingAddress;
    private PatientTO patientTOWithNewAddress;
    private PatientEntity patientEntityWithExistingAddress;
    private PatientEntity patientEntityWithNewAddress;

    @BeforeEach
    void setUp() {
        // Setting up existing address
        existingAddress = new AddressEntity();
        existingAddress.setCity("City");
        existingAddress.setAddressLine1("Street 123");
        existingAddress.setPostalCode("12345");

        existingAddressTO = addressMapper.toTO(existingAddress);

        // Ensure that the address is correctly assigned
        patientTOWithExistingAddress = new PatientTO();
        patientTOWithExistingAddress.setFirstName("John");
        patientTOWithExistingAddress.setLastName("Doe");
        patientTOWithExistingAddress.setTelephoneNumber("123456789");
        patientTOWithExistingAddress.setEmail("john.doe@example.com");
        patientTOWithExistingAddress.setPatientNumber("P12345");
        patientTOWithExistingAddress.setDateOfBirth(LocalDate.parse("1990-01-01"));
        patientTOWithExistingAddress.setIsInsured(true);
        patientTOWithExistingAddress.setAddress(existingAddressTO);

        patientEntityWithExistingAddress = new PatientEntity();
        patientEntityWithExistingAddress.setFirstName("John");
        patientEntityWithExistingAddress.setLastName("Doe");
        patientEntityWithExistingAddress.setTelephoneNumber("123456789");
        patientEntityWithExistingAddress.setEmail("john.doe@example.com");
        patientEntityWithExistingAddress.setPatientNumber("P12345");
        patientEntityWithExistingAddress.setDateOfBirth(LocalDate.parse("1990-01-01"));
        patientEntityWithExistingAddress.setIsInsured(true);
        patientEntityWithExistingAddress.setAddress(existingAddress);

        // Setting up new address
        newAddress = new AddressEntity();
        newAddress.setCity("NewCity");
        newAddress.setAddressLine1("NewStreet 123");
        newAddress.setPostalCode("54321");

        newAddressTO = addressMapper.toTO(newAddress);

        // Ensure that the address is correctly assigned for the new address scenario
        patientTOWithNewAddress = new PatientTO();
        patientTOWithNewAddress.setFirstName("Jane");
        patientTOWithNewAddress.setLastName("Smith");
        patientTOWithNewAddress.setTelephoneNumber("987654321");
        patientTOWithNewAddress.setEmail("jane.smith@example.com");
        patientTOWithNewAddress.setPatientNumber("P54321");
        patientTOWithNewAddress.setDateOfBirth(LocalDate.parse("1995-02-02"));
        patientTOWithNewAddress.setIsInsured(false);
        patientTOWithNewAddress.setAddress(newAddressTO);

        patientEntityWithNewAddress = new PatientEntity();
        patientEntityWithNewAddress.setFirstName("Jane");
        patientEntityWithNewAddress.setLastName("Smith");
        patientEntityWithNewAddress.setTelephoneNumber("987654321");
        patientEntityWithNewAddress.setEmail("jane.smith@example.com");
        patientEntityWithNewAddress.setPatientNumber("P54321");
        patientEntityWithNewAddress.setDateOfBirth(LocalDate.parse("1995-02-02"));
        patientEntityWithNewAddress.setIsInsured(false);
        patientEntityWithNewAddress.setAddress(newAddress);
    }

    @Test
    void shouldCreatePatientWithExistingAddress() {
        // Given: Address already exists in the database
        when(addressRepository.findByCityAndAddressLine1AndPostalCode(
                existingAddress.getCity(),
                existingAddress.getAddressLine1(),
                existingAddress.getPostalCode()
        )).thenReturn(Optional.of(existingAddress));

        when(patientMapper.toEntity(patientTOWithExistingAddress)).thenReturn(patientEntityWithExistingAddress);
        when(patientRepository.save(patientEntityWithExistingAddress)).thenReturn(patientEntityWithExistingAddress);
        when(patientMapper.toTo(patientEntityWithExistingAddress)).thenReturn(patientTOWithExistingAddress);

        // When: Creating patient with existing address
        PatientTO result = patientService.createPatient(patientTOWithExistingAddress);

        // Then: Check that the returned patient has the same address as the existing one
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(existingAddressTO, result.getAddress());  // Address should be the same as the existing one
    }

    @Test
    void shouldCreatePatientWithNewAddress() {
        // Given: Address does not exist in the database
        when(addressRepository.findByCityAndAddressLine1AndPostalCode(
                newAddress.getCity(),
                newAddress.getAddressLine1(),
                newAddress.getPostalCode()
        )).thenReturn(Optional.empty());  // No such address, so it should be created

        when(addressRepository.save(newAddress)).thenReturn(newAddress);
        when(patientMapper.toEntity(patientTOWithNewAddress)).thenReturn(patientEntityWithNewAddress);
        when(patientRepository.save(patientEntityWithNewAddress)).thenReturn(patientEntityWithNewAddress);
        when(patientMapper.toTo(patientEntityWithNewAddress)).thenReturn(patientTOWithNewAddress);

        // When: Creating patient with new address
        PatientTO result = patientService.createPatient(patientTOWithNewAddress);

        // Then: Check that the returned patient has the new address
        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals(newAddressTO, result.getAddress());  // The new address should be assigned
    }
}
