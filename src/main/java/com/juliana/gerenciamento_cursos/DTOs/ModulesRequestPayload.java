package com.juliana.gerenciamento_cursos.DTOs;

import com.juliana.gerenciamento_cursos.domain.Difficulty;

public record ModulesRequestPayload(String title, String description, Difficulty difficulty) {
}
