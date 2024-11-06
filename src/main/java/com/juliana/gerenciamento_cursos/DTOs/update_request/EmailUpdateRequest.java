package com.juliana.gerenciamento_cursos.DTOs.update_request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmailUpdateRequest(
        @NotNull(message = "o campo email não pode eestar vazio")
        @Email
        String email) {}
