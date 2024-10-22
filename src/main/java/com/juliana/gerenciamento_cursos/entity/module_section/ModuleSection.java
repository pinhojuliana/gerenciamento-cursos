package com.juliana.gerenciamento_cursos.entity.module_section;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.course.Dificultity;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
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

    public ModuleSection(String title, String description, String dificultity) throws InexistentOptionException{
        this.title = title;
        this.description = description;
        this.dificultity = Dificultity.validDificultityValue(dificultity);
    }

    @Override
    public String toString(){
        return String.format("{Title: %s, Description: %s, Dificultity: %s}", title, description, dificultity.getLabel());
    }

}
