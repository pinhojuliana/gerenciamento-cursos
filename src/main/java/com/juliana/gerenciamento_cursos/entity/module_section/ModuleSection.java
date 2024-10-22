package com.juliana.gerenciamento_cursos.entity.module_section;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.course.Dificultity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "module_section")
@Getter
public class ModuleSection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    private Dificultity dificultity;
    @Column(name = "course_id")
    private Course course;

    public ModuleSection(String title, String description, Dificultity dificultity, Course course) {
        this.title = title;
        this.description = description;
        this.dificultity = dificultity;
        this.course = course;
    }

    @Override
    public String toString(){
        return String.format("{Title: %s, Description: %s, Dificultity: %s}", title, description, dificultity.getLabel());
    }

}
