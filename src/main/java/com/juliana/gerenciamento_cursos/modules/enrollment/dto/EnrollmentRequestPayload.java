package com.juliana.gerenciamento_cursos.modules.enrollment.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EnrollmentRequestPayload(@NotNull UUID courseId, @NotNull UUID studentId) {
}
