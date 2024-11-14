package com.juliana.gerenciamento_cursos.DTOs.request_payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EnrollmentRequestPayload(@NotNull @NotBlank UUID courseId, @NotNull @NotBlank UUID studentId) {
}
