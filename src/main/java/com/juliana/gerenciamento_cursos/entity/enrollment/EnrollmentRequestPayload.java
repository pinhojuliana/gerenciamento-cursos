package com.juliana.gerenciamento_cursos.entity.enrollment;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.user.student.Student;

public record EnrollmentRequestPayload(Course course, Student student) {
}