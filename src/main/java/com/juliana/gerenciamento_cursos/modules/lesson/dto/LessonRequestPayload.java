package com.juliana.gerenciamento_cursos.modules.lesson.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LessonRequestPayload(
        @Schema(example = "O que é a JVM?", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @NotBlank
        String title,
        @Schema(example = "Nessa aula veremos o que é e como funciona a Java Virtual Machine", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @NotBlank
        String description,
        @Schema(example = "4600aac4-c6d6-424a-8a35-8b3180f52f5c", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        UUID unitId) {
}
