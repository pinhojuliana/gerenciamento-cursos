package com.juliana.gerenciamento_cursos.application.entity.modules;

import com.juliana.gerenciamento_cursos.application.entity.course.Course;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Modules {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public Modules(String title, String description, Difficulty difficulty, Course course) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.course = course;
    }
}
