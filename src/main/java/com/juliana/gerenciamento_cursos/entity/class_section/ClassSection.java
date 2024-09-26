package com.juliana.gerenciamento_cursos.entity.class_section;

import com.juliana.gerenciamento_cursos.entity.module_section.ModuleSection;
import lombok.Data;

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
        return String.format("{Title: %s, Description: %s\nModule: %s}", title, description, moduleSection.getTitle());
    }
}

