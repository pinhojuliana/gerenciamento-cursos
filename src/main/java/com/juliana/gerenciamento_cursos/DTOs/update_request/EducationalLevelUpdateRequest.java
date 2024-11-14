package com.juliana.gerenciamento_cursos.DTOs.update_request;

import com.juliana.gerenciamento_cursos.domain.client.EducationalLevel;
import jakarta.validation.constraints.NotNull;

public record EducationalLevelUpdateRequest(@NotNull EducationalLevel educationalLevel) {
}
