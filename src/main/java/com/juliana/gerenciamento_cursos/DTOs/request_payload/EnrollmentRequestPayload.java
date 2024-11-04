package com.juliana.gerenciamento_cursos.DTOs.request_payload;

import java.util.UUID;

public record EnrollmentRequestPayload(UUID courseId, UUID studentId) {
}
