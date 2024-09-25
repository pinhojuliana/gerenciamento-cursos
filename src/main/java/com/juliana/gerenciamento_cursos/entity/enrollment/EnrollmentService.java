package com.juliana.gerenciamento_cursos.entity.enrollment;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.user.student.Student;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EnrollmentService {
    private List<Enrollment> enrollments;

    public EnrollmentService(){
        this.enrollments = new ArrayList<>();
    }

    public void enrollStudentInCourse(Course course, Student student){
        Enrollment enrollment = new Enrollment(course, student);
        enrollments.add(enrollment);
        course.getEnrollments().add(enrollment);
        student.getStudentEnrollments().add(enrollment);
    }

    public void unsubscribleStudentOfCourse(Student student, Course course){
        enrollments.stream()
                .filter(e -> e.getStudent().equals(student) || e.getCourse().equals(course))
                .findFirst()
                .ifPresent(e -> e.setActive(false));
    }

}
