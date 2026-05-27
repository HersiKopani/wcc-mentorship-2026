package com.wcc.hospital.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Request body to register or update a patient")
public record PatientRequest(

        @NotBlank(message = "First name must not be blank")
        @Size(max = 100)
        @Schema(example = "Sarah")
        String firstName,

        @NotBlank(message = "Last name must not be blank")
        @Size(max = 100)
        @Schema(example = "Connor")
        String lastName,

        @NotBlank(message = "National ID must not be blank")
        @Schema(example = "NHS-1234567890")
        String nationalId,

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        @Schema(example = "1990-06-15")
        LocalDate dateOfBirth,

        @NotBlank(message = "Gender is required")
        @Pattern(regexp = "MALE|FEMALE|OTHER", message = "Gender must be MALE, FEMALE, or OTHER")
        @Schema(example = "FEMALE")
        String gender,

        @Schema(example = "A+")
        String bloodType,

        @Pattern(regexp = "^\\+?[0-9\\s\\-]{7,15}$", message = "Invalid phone number format")
        @Schema(example = "+44 7700 900123")
        String phoneNumber,

        @Email(message = "Invalid email address")
        @Schema(example = "sarah.connor@email.com")
        String email,

        @Schema(example = "12 Future Road, London, E1 6RF")
        String address,

        @Schema(example = "Cardiology")
        String ward,

        @Pattern(regexp = "ADMITTED|DISCHARGED|OUTPATIENT", message = "Status must be ADMITTED, DISCHARGED, or OUTPATIENT")
        @Schema(example = "ADMITTED")
        String admissionStatus,

        @Schema(example = "2024-03-01")
        LocalDate admissionDate
) {}
