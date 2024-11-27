package com.juliana.gerenciamento_cursos.DTOs.auth;

import java.time.Instant;

public record AuthStudentResponseDTO(String token, Instant expires) {
}
