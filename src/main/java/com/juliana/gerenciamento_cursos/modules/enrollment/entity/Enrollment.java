package com.juliana.gerenciamento_cursos.modules.enrollment.entity;

import com.juliana.gerenciamento_cursos.modules.course.entity.Course;
import com.juliana.gerenciamento_cursos.modules.client.entity.Student;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "enrollment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "enrollment_date",nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime enrollmentDateTime;

    @Column(name = "deadline_for_completion",nullable = false)
    private LocalDate deadlineForCompletion;

    @Column(nullable = false)
    private int duration;

    @Column
    private boolean active;

}
