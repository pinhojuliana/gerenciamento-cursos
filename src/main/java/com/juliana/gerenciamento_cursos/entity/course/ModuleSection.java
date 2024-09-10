package com.juliana.gerenciamento_cursos.entity.course;

import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ModuleSection {
    private UUID id;
    private String title;
    private String description;
    private List<ClassSection> classes = new ArrayList<>();
    private Dificultity dificultity;
    private Course course;

    public ModuleSection(String title, String description, String dificultity, int index, String courseTitle) throws InexistentOptionException{
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.dificultity = Dificultity.validDificultityValue(dificultity);
        this.course = course.getEducationalPlatform().verifyExistenceOfCourse(courseTitle);
        course.getModules().add(this);
    }

    public String toString(){
        return String.format("Title: %s\nDescription: %s\nDificultity: %s\nCourse %s", title, description, dificultity.getLabel(), course.getTitle());
    }

}
