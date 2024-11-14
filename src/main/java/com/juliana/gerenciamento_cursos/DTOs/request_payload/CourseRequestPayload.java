package com.juliana.gerenciamento_cursos.DTOs.request_payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseRequestPayload(@NotNull @NotBlank String title, @NotNull @NotBlank String description) {
}
