package com.juliana.gerenciamento_cursos.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "enrollment")
@Data
@ToString
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

    public Enrollment(Course course, Student student) {
        this.course = course;
        this.student = student;
        this.active = true;
        this.duration = 365;
        this.deadlineForCompletion = LocalDateTime.now().plusDays(duration).toLocalDate();
    }

}
