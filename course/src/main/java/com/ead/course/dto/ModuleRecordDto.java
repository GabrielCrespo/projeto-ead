package com.ead.course.dto;

import jakarta.validation.constraints.NotBlank;

public record ModuleRecordDto(

        @NotBlank
        String title,

        @NotBlank
        String description) {
}
