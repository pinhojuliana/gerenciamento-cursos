package com.juliana.gerenciamento_cursos.DTOs;

import java.time.LocalDateTime;

public record CourseDTO(String title, String description, LocalDateTime createdAt) {
}
