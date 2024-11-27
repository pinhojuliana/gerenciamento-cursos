package com.juliana.gerenciamento_cursos.modules.client.dto;

import java.time.Instant;

public record AuthStudentResponseDTO(String token, Instant expires) {
}
