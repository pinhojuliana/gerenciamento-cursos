package com.juliana.gerenciamento_cursos.DTOs.auth;

import java.time.Instant;

public record AuthTeacherResponseDTO(String token, Instant expires) {
}
