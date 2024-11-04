package com.juliana.gerenciamento_cursos.DTOs;

import java.util.UUID;

public record EnrollmentRequestPayload(UUID courseId, UUID studentId) {
}
