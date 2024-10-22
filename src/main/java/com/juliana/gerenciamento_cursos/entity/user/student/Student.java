package com.juliana.gerenciamento_cursos.entity.user.student;

import com.juliana.gerenciamento_cursos.entity.user.User;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import com.juliana.gerenciamento_cursos.validations.DateValidation;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "student")
@Getter
public class Student extends User {
    @Column
    private String description;

    public Student(String name, String username, String email, String password, LocalDate dateOfBirth, String description) throws UnderageException {
        super(name, username, email, password, dateOfBirth);
        this.description = description;
    }

    @Override
    public String toString(){
        return String.format("Name: %s\nEmail: %s\nDate of birth: %s\nCreated at: %s", name, email, DateValidation.formatDate(dateOfBirth), DateValidation.formatDateTime(createdAt));
    }

}
