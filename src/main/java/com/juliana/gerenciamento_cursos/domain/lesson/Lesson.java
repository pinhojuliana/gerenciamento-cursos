package com.juliana.gerenciamento_cursos.domain.lesson;

import com.juliana.gerenciamento_cursos.domain.unit.Unit;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "lesson")
@ToString
@NoArgsConstructor
@Data
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

