package com.juliana.gerenciamento_cursos.entity.enrollment;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.educational_platform.EducationalPlatform;
import com.juliana.gerenciamento_cursos.entity.student.Student;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
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
    private EducationalPlatform educationalPlatform;

    public Enrollment(String courseTitle, String studentEmail, EducationalPlatform educationalPlatform) throws InexistentOptionException {
        this.id = UUID.randomUUID();
        this.course = educationalPlatform.verifyExistenceOfCourse(courseTitle);
        this.student = educationalPlatform.verifyExistenceOfStudent(studentEmail);
        enrollmentDateTime = LocalDateTime.now();
        deadlineForCompletion = enrollmentDateTime.plusDays(90).toLocalDate();
        educationalPlatform.getEnrollments().add(this);
        student.getStudentEnrollments().add(this);
        course.getEnrollments().add(this);
    }

    public String toString(){
       return String.format("Course: %s\nStudent: %s\nEnrollment date: %s\nFinal date: %s", course.getTitle(), student.getEmail(), DateValidation.formatDateTime(enrollmentDateTime) , DateValidation.formatDate(deadlineForCompletion));
    }
}
