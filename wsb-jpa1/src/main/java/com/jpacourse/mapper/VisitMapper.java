package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class VisitMapper {
    public VisitTO toTo(VisitEntity visitEntity) {
        VisitTO visitDTO = new VisitTO();
        visitDTO.setVisitTime(visitEntity.getTime());
        visitDTO.setDoctorName(visitEntity.getDoctor().getFirstName() + " " + visitEntity.getDoctor().getLastName());

        visitDTO.setTreatmentTypes(visitEntity.getTreatments().stream()
                .map(treatment -> treatment.getType())
                .collect(Collectors.toList()));

        return visitDTO;
    }
}
