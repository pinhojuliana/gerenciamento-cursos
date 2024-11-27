package com.juliana.gerenciamento_cursos.modules.client.entity;

import com.juliana.gerenciamento_cursos.modules.course.entity.Course;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Teacher extends Client {
    @ElementCollection
    @CollectionTable(name = "teacher_skill", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "skill")
    private Set<String> skills;

    @ManyToMany(mappedBy = "teachers")
    private Set<Course> coursesTaught;

    public Teacher(String name, String username, String email, String password, LocalDate dateOfBirth) {
        super(name, username, email, password, dateOfBirth);
        this.skills = new HashSet<>();
        this.coursesTaught = new HashSet<>();
    }
}