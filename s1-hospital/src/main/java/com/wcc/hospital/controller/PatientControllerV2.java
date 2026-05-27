package com.wcc.hospital.controller;

import com.wcc.hospital.dto.PatientResponseV2;
import com.wcc.hospital.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/patients")
@RequiredArgsConstructor
@Tag(name = "Patients v2", description = "Full patient details with ward and admission status filters")
public class PatientControllerV2 {

    private final PatientService patientService;

    @GetMapping
    @Operation(summary = "List all patients (v2)",
            description = "Filter by ward (e.g. ICU, Cardiology) or status (ADMITTED, DISCHARGED, OUTPATIENT)")
    public ResponseEntity<List<PatientResponseV2>> getAllPatients(
            @Parameter(description = "Filter by ward name") @RequestParam(required = false) String ward,
            @Parameter(description = "Filter by admission status") @RequestParam(required = false) String status) {

        if (ward != null && !ward.isBlank()) {
            return ResponseEntity.ok(patientService.getPatientsByWard(ward));
        }
        if (status != null && !status.isBlank()) {
            return ResponseEntity.ok(patientService.getPatientsByStatus(status));
        }
        // here add both
        return ResponseEntity.ok(patientService.getAllPatientsV2());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get patient full details (v2)")
    public ResponseEntity<PatientResponseV2> getPatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientByIdV2(id));
    }
}
