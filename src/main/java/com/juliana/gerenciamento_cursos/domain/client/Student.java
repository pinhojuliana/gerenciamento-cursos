package com.juliana.gerenciamento_cursos.domain.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Student extends Client {

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "educational_level")
    private EducationalLevel educationalLevel;

    public Student(String name, String username, String email, String password, LocalDate dateOfBirth, String description, EducationalLevel educationalLevel) {
        super(name, username, email, password, dateOfBirth);
        this.description = description;
        this.educationalLevel = educationalLevel;
    }
}
