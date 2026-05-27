package com.wcc.hospital.controller;

import com.wcc.hospital.dto.PatientRequest;
import com.wcc.hospital.dto.PatientResponseV1;
import com.wcc.hospital.dto.PatientResponseV2;
import com.wcc.hospital.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Tag(name = "Patients v1", description = "Basic patient info — id, name, national ID, DOB, gender")
public class PatientControllerV1 {

    private final PatientService patientService;

    @GetMapping
    @Operation(summary = "List all patients (v1)")
    public ResponseEntity<List<PatientResponseV1>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatientsV1());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get patient by ID (v1)")
    @ApiResponse(responseCode = "404", description = "Patient not found")
    public ResponseEntity<PatientResponseV1> getPatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientByIdV1(id));
    }

    @PostMapping
    @Operation(summary = "Register a new patient", security = @SecurityRequirement(name = "Bearer Auth"))
    @ApiResponse(responseCode = "201", description = "Registered")
    @ApiResponse(responseCode = "400", description = "Validation error")
    @ApiResponse(responseCode = "401", description = "Unauthorised")
    @ApiResponse(responseCode = "409", description = "National ID already registered")
    public ResponseEntity<PatientResponseV2> registerPatient(@Valid @RequestBody PatientRequest request)  {
        PatientResponseV2 created = patientService.registerPatient(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update patient details", security = @SecurityRequirement(name = "Bearer Auth"))
    public ResponseEntity<PatientResponseV2> updatePatient(@PathVariable Long id,
                                                            @Valid @RequestBody PatientRequest request) {
        return ResponseEntity.ok(patientService.updatePatient(id, request));
    }

    @PatchMapping("/{id}/discharge")
    @Operation(summary = "Discharge a patient", security = @SecurityRequirement(name = "Bearer Auth"))
    @ApiResponse(responseCode = "204", description = "Discharged")
    public ResponseEntity<Void> dischargePatient(@PathVariable Long id) {
        patientService.dischargePatient(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient record", security = @SecurityRequirement(name = "Bearer Auth"))
    @ApiResponse(responseCode = "204", description = "Deleted")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
