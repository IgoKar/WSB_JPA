	package com.jpacourse.persistence.entity;

	import com.jpacourse.persistence.enums.TreatmentType;
	import lombok.Getter;
	import lombok.Setter;

	import javax.persistence.*;

	@Getter
	@Setter
	@Entity
	@Table(name = "medical_treatment")
	public class MedicalTreatmentEntity {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@Column(nullable = false)
		private String description;

		@Enumerated(EnumType.STRING)
		private TreatmentType type;

		@ManyToOne
		@JoinColumn(name = "visit_id", nullable = false)
		private VisitEntity visit;
	}
