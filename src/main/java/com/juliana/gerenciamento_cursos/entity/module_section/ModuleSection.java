package com.juliana.gerenciamento_cursos.entity.module_section;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.course.Dificultity;
import com.juliana.gerenciamento_cursos.entity.class_section.ClassSection;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "module_section")
@Getter
public class ModuleSection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column
    private String title;
    @Column
    private String description;
    @Enumerated(EnumType.STRING)
    private Dificultity dificultity;
    @Column(name = "course_id")
    private Course course;
    @OneToMany(mappedBy = "moduleSection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassSection> classes;

    public ModuleSection(String title, String description, String dificultity) throws InexistentOptionException{
        this.title = title;
        this.description = description;
        this.dificultity = Dificultity.validDificultityValue(dificultity);
        this.classes = new ArrayList<>();
    }

    @Override
    public String toString(){
        return String.format("{Title: %s, Description: %s, Dificultity: %s}", title, description, dificultity.getLabel());
    }

}
