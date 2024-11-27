package com.juliana.gerenciamento_cursos.modules.unit.dto;

import com.juliana.gerenciamento_cursos.modules.unit.entity.Difficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UnitRequestPayload(@NotNull @NotBlank String title, @NotNull @NotBlank String description, @NotNull Difficulty difficulty, UUID courseId) {
}
