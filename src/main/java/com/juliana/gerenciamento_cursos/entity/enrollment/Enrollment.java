package com.juliana.gerenciamento_cursos.entity.enrollment;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.user.student.Student;
import com.juliana.gerenciamento_cursos.validations.DateValidation;
import jakarta.persistence.*;
import lombok.Data;

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
    @Column(name = "course_id")
    private Course course;
    @Column(name = "student_id")
    private Student student;
    @Column
    private LocalDateTime enrollmentDateTime;
    @Column
    private LocalDate deadlineForCompletion;
    @Column
    private int duration;
    @Column
    private boolean active;

    public Enrollment(Course course, Student student, int duration) {
        this.course = course;
        this.student = student;
        this.enrollmentDateTime = LocalDateTime.now();
        this.duration = duration;
        this.deadlineForCompletion = enrollmentDateTime.plusDays(duration).toLocalDate();
        this.active = true;
    }

    @Override
    public String toString(){
       return String.format("{Course: %s, Student: %s, Enrollment date: %s, Final date: %s, Active: %s}", course.getTitle(), student.getEmail(), DateValidation.formatDateTime(enrollmentDateTime) , DateValidation.formatDate(deadlineForCompletion), isActive());
    }
}
