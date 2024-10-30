package com.juliana.gerenciamento_cursos.entity.teacher;

import java.time.LocalDate;
import java.util.Set;

public record TeacherDTO(String name, String username, String email, LocalDate dateOfBirth, Set<String> skills) {
}
