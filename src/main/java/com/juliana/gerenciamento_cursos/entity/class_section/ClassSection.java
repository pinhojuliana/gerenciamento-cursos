package com.juliana.gerenciamento_cursos.entity.class_section;

import com.juliana.gerenciamento_cursos.entity.module_section.ModuleSection;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "class_section")
@Data
public class ClassSection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column
    private String title;
    @Column
    private String description;
    @Column(name = "module_id", nullable = false)
    private ModuleSection moduleSection;

    public ClassSection(String title, String description, ModuleSection moduleSection) {
        this.title = title;
        this.description = description;
        this.moduleSection = moduleSection;
    }

    @Override
    public String toString(){
        return String.format("{Title: %s, Description: %s\nModule: %s}", title, description, moduleSection.getTitle());
    }
}

