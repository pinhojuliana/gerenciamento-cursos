package com.juliana.gerenciamento_cursos.DTOs;

import com.juliana.gerenciamento_cursos.domain.unit.Unit;

public record LessonDTO(String title, String description, Unit unitTitle) {
}
