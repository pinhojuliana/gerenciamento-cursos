package com.juliana.gerenciamento_cursos.modules.unit.dto;

import com.juliana.gerenciamento_cursos.modules.unit.entity.Difficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UnitRequestPayload(
        @Schema(example = "Introdução ao Java", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @NotBlank
        String title,
        @Schema(example = "Primeiros passos. Introdução à JVM e sintaxe básica", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @NotBlank
        String description,
        @Schema(example = "BEGINNER", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        Difficulty difficulty,
        @Schema(example = "4600aac4-c6d6-424a-8a35-8b3180f52f5c", requiredMode = Schema.RequiredMode.REQUIRED)
        UUID courseId) {
}
