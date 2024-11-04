package com.juliana.gerenciamento_cursos.DTOs.request_payload;

import com.juliana.gerenciamento_cursos.domain.modules.Difficulty;

public record ModulesRequestPayload(String title, String description, Difficulty difficulty) {
}
