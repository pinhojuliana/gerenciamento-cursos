package com.juliana.gerenciamento_cursos.entity.course.module_section;

import com.juliana.gerenciamento_cursos.entity.course.Dificultity;
import com.juliana.gerenciamento_cursos.entity.course.class_section.ClassSection;
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
    private List<ClassSection> classes;
    private Dificultity dificultity;

    public ModuleSection(String title, String description, String dificultity) throws InexistentOptionException{
        this.id = UUID.randomUUID();
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
