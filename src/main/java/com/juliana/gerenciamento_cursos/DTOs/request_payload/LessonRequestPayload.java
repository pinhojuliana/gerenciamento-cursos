package com.juliana.gerenciamento_cursos.DTOs.request_payload;

import java.util.UUID;

public record LessonRequestPayload(String title, String description, UUID unitId) {
}
