package com.juliana.gerenciamento_cursos.modules.update_request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmailUpdateRequest(
        @NotNull
        @NotBlank(message = "o campo email não pode eestar vazio")
        @Email
        String email) {}
