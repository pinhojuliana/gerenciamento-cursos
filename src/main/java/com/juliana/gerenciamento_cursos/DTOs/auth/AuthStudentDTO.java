package com.juliana.gerenciamento_cursos.DTOs.auth;

import jakarta.validation.constraints.Email;

public record AuthStudentDTO(@Email String email, String password) {
}
