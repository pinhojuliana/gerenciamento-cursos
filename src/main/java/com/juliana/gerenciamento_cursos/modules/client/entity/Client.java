package com.juliana.gerenciamento_cursos.modules.client.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class Client implements Comparable<Client>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected UUID id;

    @Column(nullable = false)
    @NotBlank(message = "O campo 'name' não pode estar vazio")
    protected String name;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "O campo 'username' não pode estar vazio")
    @Pattern(regexp = "^[a-z0-9_.&$]{5,10}$",
            message = "O nome do usuario deve ter entre 5 e 10 caracteres, deve ser unico e não pode conter espaços. Pode conter numeros, letras minusculas e caracteres especiais (_), (.), (&) e ($)")
    protected String username;

    @Column
    @NotBlank(message = "O campo 'senha' não pode estar vazio")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
            message = "A senha deve ter pelo menos 8 caracteres, 1 número e 1 caractere especial (!,@,#,$,%,&,*)")
    protected String password;

    @Column(unique = true, nullable = false)
    @Email
    @NotBlank(message = "O campo 'email' não pode estar vazio")
    protected String email;

    @Column(name = "date_of_birth", nullable = false)
    @Past
    protected LocalDate dateOfBirth;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    protected LocalDateTime createdAt;

    public Client(String name, String username, String email, String password, LocalDate dateOfBirth) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public int compareTo(Client client) {
        return this.getName().compareTo(client.getName());
    }
}
