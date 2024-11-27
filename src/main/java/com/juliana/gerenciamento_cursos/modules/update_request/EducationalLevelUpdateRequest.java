package com.juliana.gerenciamento_cursos.modules.update_request;

import com.juliana.gerenciamento_cursos.modules.client.entity.EducationalLevel;
import jakarta.validation.constraints.NotNull;

public record EducationalLevelUpdateRequest(@NotNull EducationalLevel educationalLevel) {
}
