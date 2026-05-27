package com.wcc.hospital.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Patient response (v2) — full details including ward, admission status, blood type")
public record PatientResponseV2(
        Long id,
        String firstName,
        String lastName,
        String nationalId,
        LocalDate dateOfBirth,
        String gender,
        String bloodType,
        String phoneNumber,
        String email,
        String address,
        String ward,
        String admissionStatus,
        LocalDate admissionDate
) {}
