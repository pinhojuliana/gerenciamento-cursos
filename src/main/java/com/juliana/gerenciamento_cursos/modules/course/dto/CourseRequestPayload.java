package com.juliana.gerenciamento_cursos.modules.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseRequestPayload(@NotNull @NotBlank String title, @NotNull @NotBlank String description) {
}
