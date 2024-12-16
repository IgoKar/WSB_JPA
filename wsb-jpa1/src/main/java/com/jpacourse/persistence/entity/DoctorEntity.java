package com.jpacourse.persistence.entity;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.jpacourse.persistence.enums.Specialization;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "doctor")
public class DoctorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "firstname", nullable = false)
	private String firstName;

	@Column(name = "lastname", nullable = false)
	private String lastName;

	@Column(name ="telephone_number", nullable = false)
	private String telephoneNumber;

	private String email;

	@Column(name ="doctor_number", nullable = false)
	private String doctorNumber;

	@OneToOne
	@JoinColumn(name = "address_id", nullable = false)
	private AddressEntity address;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Specialization specialization;

	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
	private List<VisitEntity> visits;
}
