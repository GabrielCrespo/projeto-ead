package com.ead.authuser.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record InstructorDto(@NotNull(message = "UserId is mandatory") UUID userId) {
}
