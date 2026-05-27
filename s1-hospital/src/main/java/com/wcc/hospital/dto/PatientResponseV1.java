package com.wcc.hospital.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Patient response (v1) — basic identification info")
public record PatientResponseV1(
        @Schema(example = "1") Long id,
        @Schema(example = "Sarah") String firstName,
        @Schema(example = "Connor") String lastName,
        @Schema(example = "NHS-1234567890") String nationalId,
        @Schema(example = "1990-06-15") LocalDate dateOfBirth,
        @Schema(example = "FEMALE") String gender
) {}
