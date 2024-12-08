package com.jpacourse.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "patient")
public class PatientEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "firstname", nullable = false)
	private String firstName;

	@Column(name = "lastname", nullable = false)
	private String lastName;

	@Column(name = "telephone_number", nullable = false)
	private String telephoneNumber;

	private String email;

	@Column(name = "patient_number", nullable = false)
	private String patientNumber;

	@Column(name = "date_of_birth",nullable = false)
	private LocalDate dateOfBirth;

	@ManyToOne
	@JoinColumn(name = "address_id", nullable = false)
	private AddressEntity address;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
	private List<VisitEntity> visits;

}
