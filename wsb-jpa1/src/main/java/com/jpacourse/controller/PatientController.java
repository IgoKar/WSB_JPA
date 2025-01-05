package com.jpacourse.controller;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<PatientTO> getPatientById(@PathVariable Long id) {
        Optional<PatientTO> patientTO = patientService.getPatientById(id);
        return patientTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PatientTO> createPatient(@RequestBody PatientTO patientTO) {
        PatientTO createdPatient = patientService.createPatient(patientTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientTO> updatePatient(@PathVariable Long id, @RequestBody PatientTO patientTO) {
        Optional<PatientTO> updatedPatient = patientService.updatePatient(id, patientTO);
        return updatedPatient.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        if (patientService.deletePatient(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
