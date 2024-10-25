package com.juliana.gerenciamento_cursos.entity.enrollment;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.user.student.Student;
import jakarta.validation.constraints.NotNull;

public record EnrollmentRequestPayload(@NotNull Course course, @NotNull Student student) {
}
