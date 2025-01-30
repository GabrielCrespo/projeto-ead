package com.ead.authuser.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserCourseDto(UUID userId, @NotNull(message = "CourseId is mandatory") UUID courseId) {
}
