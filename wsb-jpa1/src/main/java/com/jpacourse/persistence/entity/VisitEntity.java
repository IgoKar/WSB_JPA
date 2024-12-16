package com.jpacourse.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "visit")
public class VisitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	private LocalDateTime time;

	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = false)
	private PatientEntity patient;

	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = false)
	private DoctorEntity doctor;

	@OneToMany(mappedBy = "visit", cascade = CascadeType.ALL)
	private List<MedicalTreatmentEntity> treatments;
}
