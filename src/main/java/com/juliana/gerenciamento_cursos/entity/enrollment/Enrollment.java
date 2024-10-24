package com.juliana.gerenciamento_cursos.entity.enrollment;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.user.student.Student;
import com.juliana.gerenciamento_cursos.util.DateValidation;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "enrollment")
@Data
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
    private boolean active = true;

    public Enrollment(Course course, Student student) {
        this.course = course;
        this.student = student;
        this.duration = 365;
        this.deadlineForCompletion = LocalDateTime.now().plusDays(duration).toLocalDate();
    }

    public Enrollment(Course course, Student student, int duration) {
        this.course = course;
        this.student = student;
        this.duration = duration;
        this.deadlineForCompletion = LocalDateTime.now().plusDays(duration).toLocalDate();
    }

    @Override
    public String toString(){
       return String.format("{Course: %s, Student: %s, Enrollment date: %s, Final date: %s, Active: %s}", course.getTitle(), student.getEmail(), DateValidation.formatDateTime(enrollmentDateTime) , DateValidation.formatDate(deadlineForCompletion), isActive());
    }
}
