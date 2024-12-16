package com.jpacourse.dto;

import com.jpacourse.persistence.enums.TreatmentType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PatientTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private String email;
    private String patientNumber;
    private Boolean isInsured;
    private LocalDate dateOfBirth;

    private List<VisitTO> visits;
}
