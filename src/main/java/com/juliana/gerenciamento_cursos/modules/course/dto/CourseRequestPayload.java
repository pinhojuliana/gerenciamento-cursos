package com.juliana.gerenciamento_cursos.modules.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseRequestPayload(
        @Schema(example = "Phyton", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @NotBlank
        String title,
        @Schema(example = "Curso completo sobre uma das linguagens mais famosas. Aprenda a fazer aplicações modernas com phyton.", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @NotBlank
        String description) {
}
