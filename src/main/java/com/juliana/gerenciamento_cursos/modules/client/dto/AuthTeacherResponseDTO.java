package com.juliana.gerenciamento_cursos.modules.client.dto;

import java.time.Instant;

public record AuthTeacherResponseDTO(String token, Instant expires) {
}
