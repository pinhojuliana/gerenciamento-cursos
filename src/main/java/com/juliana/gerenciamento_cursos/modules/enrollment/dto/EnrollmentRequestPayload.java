package com.juliana.gerenciamento_cursos.modules.enrollment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EnrollmentRequestPayload(
        @Schema(example = "f7d3d2b0-7a8c-4fb8-a97d-108f413cd662", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        UUID courseId,
        @Schema(example = "6b5e9d3a-f107-4ad7-a077-e2c71c32fffa", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        UUID studentId) {
}
