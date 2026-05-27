package com.wcc.hospital.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JWT token response")
public record AuthResponse(
        @Schema(description = "Bearer token") String token,
        @Schema(example = "Bearer") String type,
        @Schema(description = "Username") String username
) {
    public static AuthResponse of(String token, String username) {
        return new AuthResponse(token, "Bearer", username);
    }
}
