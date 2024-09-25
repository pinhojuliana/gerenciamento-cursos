package com.juliana.gerenciamento_cursos.entity.user;

import com.juliana.gerenciamento_cursos.exceptions.InvalidEmailException;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import com.juliana.gerenciamento_cursos.validations.AgeValidation;
import com.juliana.gerenciamento_cursos.validations.EmailValidation;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class User {
    protected UUID id;
    protected String name;
    protected String email;
    protected LocalDate dateOfBirth;
    protected LocalDateTime createdAt;

    public User(String name, String email, LocalDate dateOfBirth) throws InvalidEmailException, UnderageException {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = EmailValidation.isValidEmail(email);
        if(AgeValidation.validateAge(dateOfBirth)){
            this.dateOfBirth = dateOfBirth;
        }
        this.createdAt = LocalDateTime.now();
    }
}
