package com.juliana.gerenciamento_cursos.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PasswordUpdateRequest(
        @NotNull
        @NotBlank
        String oldPassword,

        @NotNull
        @NotBlank
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
                message = "A senha deve ter pelo menos 8 caracteres, 1 número e 1 caractere especial (!,@,#,$,%,&,*)"
        )
        String newPassword
) {}