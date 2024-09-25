package com.juliana.gerenciamento_cursos.entity.enrollment;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.user.Student;
import com.juliana.gerenciamento_cursos.validations.DateValidation;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Enrollment {
    private UUID id;
    private Course course;
    private Student student;
    private LocalDateTime enrollmentDateTime;
    private LocalDate deadlineForCompletion;
    private boolean active;

    public Enrollment(Course course, Student student) {
        this.id = UUID.randomUUID();
        this.course = course;
        this.student = student;
        enrollmentDateTime = LocalDateTime.now();
        deadlineForCompletion = enrollmentDateTime.plusDays(90).toLocalDate();
        active = true;
    }

    @Override
    public String toString(){
       return String.format("{Course: %s, Student: %s, Enrollment date: %s, Final date: %s, Active: %s}", course.getTitle(), student.getEmail(), DateValidation.formatDateTime(enrollmentDateTime) , DateValidation.formatDate(deadlineForCompletion), isActive());
    }
}
