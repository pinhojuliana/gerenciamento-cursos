package com.juliana.gerenciamento_cursos.modules.client.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthTeacherDTO(@NotBlank(message = "O campo não pode estar vazio")
                             String username,
                             @NotBlank(message = "O campo não pode estar vazio")
                             String password) {
}