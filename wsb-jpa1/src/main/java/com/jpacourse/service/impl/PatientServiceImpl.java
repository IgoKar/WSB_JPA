package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.repository.AddressRepository;
import com.jpacourse.repository.PatientRepository;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientMapper patientMapper;

    public Optional<PatientTO> getPatientById(Long id) {
        return patientRepository.findById(id).map(patientMapper::toTo);
    }

    @Override
    public PatientTO createPatient(PatientTO patientTO) {
        AddressEntity addressEntity = addressRepository.findByCityAndAddressLine1AndPostalCode(
                patientTO.getAddress().getCity(),
                patientTO.getAddress().getAddressLine1(),
                patientTO.getAddress().getPostalCode()
        ).orElseGet(() -> {
            AddressEntity newAddress = new AddressEntity();
            newAddress.setCity(patientTO.getAddress().getCity());
            newAddress.setAddressLine1(patientTO.getAddress().getAddressLine1());
            newAddress.setAddressLine2(patientTO.getAddress().getAddressLine2());
            newAddress.setPostalCode(patientTO.getAddress().getPostalCode());
            return addressRepository.save(newAddress);
        });

        PatientEntity patientEntity = patientMapper.toEntity(patientTO);
        patientEntity.setAddress(addressEntity);

        PatientEntity savedEntity = patientRepository.save(patientEntity);

        return patientMapper.toTo(savedEntity);
    }

    @Override
    public Optional<PatientTO> updatePatient(Long id, PatientTO patientTO) {
        return patientRepository.findById(id).map(existingEntity -> {
            existingEntity.setFirstName(patientTO.getFirstName());
            existingEntity.setLastName(patientTO.getLastName());
            existingEntity.setTelephoneNumber(patientTO.getTelephoneNumber());
            existingEntity.setEmail(patientTO.getEmail());
            existingEntity.setPatientNumber(patientTO.getPatientNumber());
            existingEntity.setDateOfBirth(patientTO.getDateOfBirth());
            existingEntity.setIsInsured(patientTO.getIsInsured());

            PatientEntity updatedEntity = patientRepository.save(existingEntity);

            return patientMapper.toTo(updatedEntity);
        });
    }

    @Override
    public boolean deletePatient(Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
