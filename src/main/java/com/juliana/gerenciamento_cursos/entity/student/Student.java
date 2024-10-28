package com.juliana.gerenciamento_cursos.entity.student;

import com.juliana.gerenciamento_cursos.entity.user.User;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "student")
@Getter
@Setter
@ToString
public class Student extends User {
    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "educational_level")
    private EducationalLevel educationalLevel;

    public Student(String name, String username, String email, String password, LocalDate dateOfBirth, String description, EducationalLevel educationalLevel) throws UnderageException {
        super(name, username, email, password, dateOfBirth);
        this.description = description;
        this.educationalLevel = educationalLevel;
    }

    public Student(String name, String username, String email, LocalDate dateOfBirth, String description, EducationalLevel educationalLevel) throws UnderageException {
        super(name, username, email, dateOfBirth);
        this.description = description;
        this.educationalLevel = educationalLevel;
    }
}
