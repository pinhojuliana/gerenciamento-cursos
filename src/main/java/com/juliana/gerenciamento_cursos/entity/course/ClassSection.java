package com.juliana.gerenciamento_cursos.entity.course;

import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import lombok.Data;

import java.io.File;
import java.util.UUID;

@Data
public class ClassSection {
    private UUID id;
    private String title;
    private String description;
    private ModuleSection moduleSection;

    public ClassSection(String title, String description, ModuleSection moduleSection) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.moduleSection = moduleSection;
    }

    @Override
    public String toString(){
        return String.format("Title: %s\nDescription: %s\nModule: %s", title, description, moduleSection.getTitle());
    }
}

