package com.juliana.gerenciamento_cursos.DTOs;

import com.juliana.gerenciamento_cursos.domain.modules.Difficulty;

public record ModulesRequestPayload(String title, String description, Difficulty difficulty) {
}
