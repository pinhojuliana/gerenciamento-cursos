package com.juliana.gerenciamento_cursos.modules.unit.entity;

import com.juliana.gerenciamento_cursos.modules.course.entity.Course;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "unit")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

}
