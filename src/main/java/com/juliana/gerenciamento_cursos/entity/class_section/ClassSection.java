package com.juliana.gerenciamento_cursos.application.entity.class_section;

import com.juliana.gerenciamento_cursos.application.entity.modules.Modules;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "class_section")
@ToString
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
    private Modules modules;


    public ClassSection(String title, String description, Modules modules) {
        this.title = title;
        this.description = description;
        this.modules = modules;
    }
}

