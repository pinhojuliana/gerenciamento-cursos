package com.juliana.gerenciamento_cursos.DTOs.update_request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UsernameUpdateRequest(
    @NotNull(message = "O nome de usuário é obrigatório")
    @Pattern(regexp = "^[a-z0-9_.&$]{5,10}$",
            message = "O nome do usuário deve ter entre 5 e 10 caracteres e deve ser único.")
    String username) {}
