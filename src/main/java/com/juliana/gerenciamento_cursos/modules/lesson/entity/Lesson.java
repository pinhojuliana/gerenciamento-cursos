package com.juliana.gerenciamento_cursos.modules.lesson.entity;

import com.juliana.gerenciamento_cursos.modules.unit.entity.Unit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "lesson")
@ToString
@NoArgsConstructor
@Getter
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    public Lesson(String title, String description, Unit unit) {
        this.title = title;
        this.description = description;
        this.unit = unit;
    }
}

