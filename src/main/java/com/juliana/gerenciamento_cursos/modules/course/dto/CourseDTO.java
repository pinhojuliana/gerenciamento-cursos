package com.juliana.gerenciamento_cursos.modules.course.dto;

import java.time.LocalDateTime;

public record CourseDTO(String title, String description, LocalDateTime createdAt) {
}
