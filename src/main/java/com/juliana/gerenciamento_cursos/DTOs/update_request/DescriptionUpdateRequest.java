package com.juliana.gerenciamento_cursos.DTOs.update_request;

import jakarta.validation.constraints.NotNull;

public record DescriptionUpdateRequest(@NotNull String description) {
}
