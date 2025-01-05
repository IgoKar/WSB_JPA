package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private AddressDao addressDao;
    @Autowired
    private PatientDao patientDao;
    @Autowired
    private PatientMapper patientMapper;

    @Override
    public Optional<PatientTO> getPatientById(Long id) {
        return Optional.ofNullable(patientDao.findOne(id))
                .map(patientMapper::toTo);
    }

    @Override
    public PatientTO createPatient(PatientTO patientTO) {
        AddressEntity addressEntity = addressDao.findByCityAndAddressLine1AndPostalCode(
                patientTO.getAddress().getCity(),
                patientTO.getAddress().getAddressLine1(),
                patientTO.getAddress().getPostalCode()
        ).orElseGet(() -> {
            AddressEntity newAddress = new AddressEntity();
            newAddress.setCity(patientTO.getAddress().getCity());
            newAddress.setAddressLine1(patientTO.getAddress().getAddressLine1());
            newAddress.setAddressLine2(patientTO.getAddress().getAddressLine2());
            newAddress.setPostalCode(patientTO.getAddress().getPostalCode());
            return addressDao.save(newAddress);
        });

        PatientEntity patientEntity = patientMapper.toEntity(patientTO);
        patientEntity.setAddress(addressEntity);

        PatientEntity savedEntity = patientDao.save(patientEntity);

        return patientMapper.toTo(savedEntity);
    }

    @Override
    public Optional<PatientTO> updatePatient(Long id, PatientTO patientTO) {
        PatientEntity existingEntity = patientDao.findOne(id);
        if (existingEntity != null) {
            existingEntity.setFirstName(patientTO.getFirstName());
            existingEntity.setLastName(patientTO.getLastName());
            existingEntity.setTelephoneNumber(patientTO.getTelephoneNumber());
            existingEntity.setEmail(patientTO.getEmail());
            existingEntity.setPatientNumber(patientTO.getPatientNumber());
            existingEntity.setDateOfBirth(patientTO.getDateOfBirth());
            existingEntity.setIsInsured(patientTO.getIsInsured());

            PatientEntity updatedEntity = patientDao.save(existingEntity);

            return Optional.of(patientMapper.toTo(updatedEntity));
        }
        return Optional.empty();
    }

    @Override
    public boolean deletePatient(Long id) {
        if (patientDao.exists(id)) {
            patientDao.delete(id);
            return true;
        }
        return false;
    }
}
