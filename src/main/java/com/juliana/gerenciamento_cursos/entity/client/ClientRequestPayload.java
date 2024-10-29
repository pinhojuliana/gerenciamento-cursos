package com.juliana.gerenciamento_cursos.entity.client;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ClientRequestPayload(
        @NotNull(message = "O nome é obrigatório")
        @Size(min = 2, message = "O nome deve ter no mínimo 2 caracteres")
        String name,

        @NotNull(message = "O nome de usuário é obrigatório")
        @Pattern(regexp = "^[a-z0-9_.&$]{5,10}$",
                message = "O nome do usuário deve ter entre 5 e 10 caracteres e deve ser único. Pode conter números, letras minúsculas e caracteres especiais (_), (.), (&) e ($)")
        String username,

        @NotNull(message = "O e-mail é obrigatório")
        @Email(message = "O e-mail deve ser válido")
        String email,

        @NotBlank(message = "A senha não pode estar vazia")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
                message = "A senha deve ter pelo menos 8 caracteres, 1 número e 1 caractere especial (!,@,#,$,%,&,*)")
        String password,

        @NotNull(message = "A data de nascimento é obrigatória")
        @Past(message = "A data de nascimento deve estar no passado")
        LocalDate dateOfBirth
) {
}
