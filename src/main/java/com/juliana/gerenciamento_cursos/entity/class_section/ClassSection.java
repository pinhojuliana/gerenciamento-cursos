package com.juliana.gerenciamento_cursos.entity.class_section;

import com.juliana.gerenciamento_cursos.entity.modules.Modules;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "class_section")
@ToString
@NoArgsConstructor
@Data
public class ClassSection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column
    private String title;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Modules modules;


    public ClassSection(String title, String description, Modules modules) {
        this.title = title;
        this.description = description;
        this.modules = modules;
    }
}

