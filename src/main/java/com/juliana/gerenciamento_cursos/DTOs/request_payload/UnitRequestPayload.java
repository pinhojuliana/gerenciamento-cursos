package com.juliana.gerenciamento_cursos.DTOs.request_payload;

import com.juliana.gerenciamento_cursos.domain.unit.Difficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UnitRequestPayload(@NotNull @NotBlank String title, @NotNull @NotBlank String description, @NotNull Difficulty difficulty, UUID courseId) {
}
