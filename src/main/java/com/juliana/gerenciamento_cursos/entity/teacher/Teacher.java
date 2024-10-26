package com.juliana.gerenciamento_cursos.entity.teacher;

import com.juliana.gerenciamento_cursos.entity.user.User;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@ToString
public class Teacher extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected UUID id;

    @ElementCollection
    @CollectionTable(name = "teacher_skill", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "skill")
    private Set<String> skills;

    public Teacher(String name, String username, String email, String password, LocalDate dateOfBirth) throws UnderageException {
        super(name, username, email, password, dateOfBirth);
        this.skills = new HashSet<>();
    }

    public Teacher(String name, String username, String email, LocalDate dateOfBirth, Set<String> skills) throws UnderageException {
        super(name, username, email, dateOfBirth);
        this.skills = skills;
    }
}
