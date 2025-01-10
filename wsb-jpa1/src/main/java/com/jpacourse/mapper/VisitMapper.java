package com.jpacourse.mapper;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class VisitMapper {
    public static VisitTO toTo(VisitEntity visitEntity) {
        VisitTO visitTO = new VisitTO();
        visitTO.setVisitTime(visitEntity.getTime());
        visitTO.setDoctorName(visitEntity.getDoctor().getFirstName() + " " + visitEntity.getDoctor().getLastName());
        visitTO.setDescription(visitEntity.getDescription());

        if(visitEntity.getTreatments() != null) {
            visitTO.setTreatmentTypes(visitEntity.getTreatments().stream()
                    .map(MedicalTreatmentEntity::getType)
                    .collect(Collectors.toList()));
        } else {
            visitTO.setTreatmentTypes(new ArrayList<>());
        }

        return visitTO;
    }
}
