package com.juliana.gerenciamento_cursos.entity.user;

import com.juliana.gerenciamento_cursos.exceptions.InvalidEmailException;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import com.juliana.gerenciamento_cursos.validations.AgeValidation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
public class User implements Comparable<User>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected UUID id;

    @Column(nullable = false)
    protected String name;

    @Column
    @NotBlank(message = "A senha não pode estar vazia")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
            message = "A senha deve ter pelo menos 8 caracteres, 1 número e 1 caractere especial (!,@,#,$,%,&,*)")
    protected String password;

    @Column(unique = true, nullable = false)
    @Email
    protected String email;

    @Column(name = "date_of_birth", nullable = false)
    protected LocalDate dateOfBirth;

    @Column(name = "created_at", nullable = false)
    protected LocalDateTime createdAt;

    public User(String name, String email, String password, LocalDate dateOfBirth) throws InvalidEmailException, UnderageException {
        this.name = name;
        this.email = email;
        this.password = password;
        if(AgeValidation.validateAge(dateOfBirth)){ //verificar
            this.dateOfBirth = dateOfBirth;
        }
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public int compareTo(User user) {
        return this.getName().compareTo(user.getName());
    }
}
