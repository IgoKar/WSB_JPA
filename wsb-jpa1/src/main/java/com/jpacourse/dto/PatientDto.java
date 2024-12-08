package com.jpacourse.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PatientDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private String email;
    private String patientNumber;
    private LocalDate dateOfBirth;
    private Boolean isInsured;

    private List<VisitDto> visits;

    @Getter
    @Setter
    public static class VisitDto {
        private LocalDateTime visitTime;
        private String doctorName;
        private List<String> treatmentTypes;
    }
}
