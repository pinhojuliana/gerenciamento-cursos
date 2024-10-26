package com.juliana.gerenciamento_cursos.entity.user;

import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import com.juliana.gerenciamento_cursos.util.AgeValidation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public class User implements Comparable<User>{

    @Column(nullable = false)
    protected String name;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[a-z0-9_.&$]{5,10}$",
            message = "O nome do usuario deve ter entre 5 e 10 caracteres, deve ser unico e não pode conter espaços. Pode conter numeros, letras minusculas e caracteres especiais (_), (.), (&) e ($)")
    protected String username;

    @Column
    @NotBlank(message = "A senha não pode estar vazia")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
            message = "A senha deve ter pelo menos 8 caracteres, 1 número e 1 caractere especial (!,@,#,$,%,&,*)")
    protected String password;

    @Column(unique = true, nullable = false)
    @Email
    protected String email;

    @Column(name = "date_of_birth", nullable = false)
    @Past
    protected LocalDate dateOfBirth;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    protected LocalDateTime createdAt;

    public User(String name, String username, String email, String password, LocalDate dateOfBirth) throws UnderageException {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateOfBirth = AgeValidation.validateAge(dateOfBirth);
    }

    public User(String name, String username, String email, LocalDate dateOfBirth) throws UnderageException {
        this.name = name;
        this.username = username;
        this.email = email;
        this.dateOfBirth = AgeValidation.validateAge(dateOfBirth);
    }

    @Override
    public int compareTo(User user) {
        return this.getName().compareTo(user.getName());
    }
}
