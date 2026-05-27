package com.wcc.hospital.service;

import com.wcc.hospital.dto.PatientRequest;
import com.wcc.hospital.dto.PatientResponseV1;
import com.wcc.hospital.dto.PatientResponseV2;
import com.wcc.hospital.exception.DuplicateNationalIdException;
import com.wcc.hospital.exception.PatientNotFoundException;
import com.wcc.hospital.model.Patient;
import com.wcc.hospital.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository repository;

    // ── V1 ────────────────────────────────────────────────────────────────────

    public List<PatientResponseV1> getAllPatientsV1() {
        return repository.findAll().stream().map(this::toV1).toList();
    }

    public PatientResponseV1 getPatientByIdV1(Long id) {
        return repository.findById(id).map(this::toV1)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }

    // ── V2 ────────────────────────────────────────────────────────────────────

    public List<PatientResponseV2> getAllPatientsV2() {
        return repository.findAll().stream().map(this::toV2).toList();
    }

    public PatientResponseV2 getPatientByIdV2(Long id) {
        return repository.findById(id).map(this::toV2)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }

    public List<PatientResponseV2> getPatientsByWard(String ward) {
        return repository.findByWard(ward).stream().map(this::toV2).toList();
    }

    public List<PatientResponseV2> getPatientsByStatus(String status) {
        return repository.findByAdmissionStatus(status).stream().map(this::toV2).toList();
    }

    // ── Mutations ─────────────────────────────────────────────────────────────

    public PatientResponseV2 registerPatient(PatientRequest request) {
        repository.findByNationalId(request.nationalId()).ifPresent(existing -> {
            throw new DuplicateNationalIdException(request.nationalId());
        });

        Patient patient = Patient.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .nationalId(request.nationalId())
                .dateOfBirth(request.dateOfBirth())
                .gender(request.gender())
                .bloodType(request.bloodType())
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .address(request.address())
                .ward(request.ward())
                .admissionStatus(request.admissionStatus() != null ? request.admissionStatus() : "OUTPATIENT")
                .admissionDate(request.admissionDate() != null ? request.admissionDate() : LocalDate.now())
                .build();

        return toV2(repository.save(patient));
    }

    public PatientResponseV2 updatePatient(Long id, PatientRequest request) {
        Patient existing = repository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        if (!existing.getNationalId().equals(request.nationalId())) {
            repository.findByNationalId(request.nationalId()).ifPresent(other -> {
                throw new DuplicateNationalIdException(request.nationalId());
            });
        }

        existing.setFirstName(request.firstName());
        existing.setLastName(request.lastName());
        existing.setNationalId(request.nationalId());
        existing.setDateOfBirth(request.dateOfBirth());
        existing.setGender(request.gender());
        existing.setBloodType(request.bloodType());
        existing.setPhoneNumber(request.phoneNumber());
        existing.setEmail(request.email());
        existing.setAddress(request.address());
        existing.setWard(request.ward());
        if (request.admissionStatus() != null) existing.setAdmissionStatus(request.admissionStatus());
        if (request.admissionDate() != null) existing.setAdmissionDate(request.admissionDate());

        return toV2(repository.save(existing));
    }

    public void dischargePatient(Long id) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        patient.setAdmissionStatus("DISCHARGED");
        repository.save(patient);
    }

    public void deletePatient(Long id) {
        if (!repository.existsById(id)) throw new PatientNotFoundException(id);
        repository.deleteById(id);
    }

    // ── Mappers ───────────────────────────────────────────────────────────────

    private PatientResponseV1 toV1(Patient p) {
        return new PatientResponseV1(p.getId(), p.getFirstName(), p.getLastName(),
                p.getNationalId(), p.getDateOfBirth(), p.getGender());
    }

    private PatientResponseV2 toV2(Patient p) {
        return new PatientResponseV2(p.getId(), p.getFirstName(), p.getLastName(),
                p.getNationalId(), p.getDateOfBirth(), p.getGender(), p.getBloodType(),
                p.getPhoneNumber(), p.getEmail(), p.getAddress(),
                p.getWard(), p.getAdmissionStatus(), p.getAdmissionDate());
    }
}
