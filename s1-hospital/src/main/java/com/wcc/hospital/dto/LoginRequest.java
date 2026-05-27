package com.wcc.hospital.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Login credentials")
public record LoginRequest(
        @NotBlank @Schema(example = "doctor") String username,
        @NotBlank @Schema(example = "password") String password
) {}
