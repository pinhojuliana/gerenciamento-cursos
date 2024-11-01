package com.juliana.gerenciamento_cursos.DTOs;

import com.juliana.gerenciamento_cursos.domain.EducationalLevel;

import java.time.LocalDate;

public record StudentDTO(String name, String username, String email, LocalDate dateOfBirth, String description, EducationalLevel educationalLevel) {
}
