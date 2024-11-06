package com.juliana.gerenciamento_cursos.DTOs.request_payload;

import com.juliana.gerenciamento_cursos.domain.unit.Difficulty;

import java.util.UUID;

public record UnitRequestPayload(String title, String description, Difficulty difficulty, UUID courseId) {
}
