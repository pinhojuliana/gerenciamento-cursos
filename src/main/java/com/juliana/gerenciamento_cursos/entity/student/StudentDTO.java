package com.juliana.gerenciamento_cursos.entity.student;

import java.time.LocalDate;
import java.util.UUID;

public record StudentDTO(String name, String username, String email, LocalDate dateOfBirth, String description, EducationalLevel educationalLevel) {
}
