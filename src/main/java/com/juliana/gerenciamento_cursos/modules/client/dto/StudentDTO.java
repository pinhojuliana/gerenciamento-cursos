package com.juliana.gerenciamento_cursos.modules.client.dto;

import com.juliana.gerenciamento_cursos.modules.client.entity.EducationalLevel;

import java.time.LocalDate;

public record StudentDTO(String name, String username, String email, LocalDate dateOfBirth, String description, EducationalLevel educationalLevel) {
}
