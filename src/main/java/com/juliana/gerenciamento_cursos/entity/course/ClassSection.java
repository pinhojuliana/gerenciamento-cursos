package com.juliana.gerenciamento_cursos.entity.course;

import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import lombok.Data;

import java.util.UUID;

@Data
public class ClassSection {
    private UUID id;
    private String title;
    private String description;
    private ModuleSection moduleSection;

    public ClassSection(String title, String description, int index, String moduleTitle) throws InexistentOptionException {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.moduleSection = moduleSection.getCourse().verifyExistenceOfModule(moduleTitle);
        moduleSection.getClasses().add(this);
    }
}

