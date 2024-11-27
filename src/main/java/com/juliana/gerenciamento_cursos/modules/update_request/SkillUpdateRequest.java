package com.juliana.gerenciamento_cursos.modules.update_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SkillUpdateRequest(@NotNull @NotBlank String skill) {
}
