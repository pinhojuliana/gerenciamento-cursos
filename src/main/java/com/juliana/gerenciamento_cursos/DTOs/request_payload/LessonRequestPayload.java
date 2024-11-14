package com.juliana.gerenciamento_cursos.DTOs.request_payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LessonRequestPayload(@NotNull @NotBlank String title, @NotNull @NotBlank String description, UUID unitId) {
}
