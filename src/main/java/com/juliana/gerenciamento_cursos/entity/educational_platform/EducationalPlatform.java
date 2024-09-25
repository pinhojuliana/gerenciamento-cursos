package com.juliana.gerenciamento_cursos.entity.educational_platform;

import com.juliana.gerenciamento_cursos.entity.course.CourseService;
import com.juliana.gerenciamento_cursos.entity.enrollment.EnrollmentService;
import com.juliana.gerenciamento_cursos.entity.user.student.StudentService;
import com.juliana.gerenciamento_cursos.entity.user.teacher.TeacherService;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class EducationalPlatform {
    private CourseService courseList;
    private EnrollmentService enrollmentList;
    private TeacherService teacherList;
    private StudentService studentList;

}

