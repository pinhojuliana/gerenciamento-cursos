package com.juliana.gerenciamento_cursos.entity.enrollment;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.educational_platform.EducationalPlatform;
import com.juliana.gerenciamento_cursos.entity.student.Student;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;

import java.time.LocalDate;
import java.util.UUID;

public class Enrollment {
    private UUID id;
    private Course course;
    private Student student;
    private LocalDate enrollmentDay;
    private LocalDate deadlineForCompletion;
    private EducationalPlatform educationalPlatform;

    public Enrollment(String courseTitle, String studentEmail, EducationalPlatform educationalPlatform) throws InexistentOptionException {
        this.id = UUID.randomUUID();
        this.course = educationalPlatform.verifyExistenceOfCourse(courseTitle);
        this.student = educationalPlatform.verifyExistenceOfStudent(studentEmail);
        enrollmentDay = LocalDate.now();
        deadlineForCompletion = enrollmentDay.plusDays(60);
        educationalPlatform.getEnrollments().add(this);
        student.getStudentEnrollments().add(this);
        course.getEnrollments().add(this);
    }
}
