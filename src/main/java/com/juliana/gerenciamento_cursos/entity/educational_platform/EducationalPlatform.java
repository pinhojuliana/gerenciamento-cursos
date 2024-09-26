package com.juliana.gerenciamento_cursos.entity.educational_platform;

import com.juliana.gerenciamento_cursos.entity.course.CourseService;
import com.juliana.gerenciamento_cursos.entity.enrollment.EnrollmentService;
import com.juliana.gerenciamento_cursos.entity.user.student.StudentService;
import com.juliana.gerenciamento_cursos.entity.user.teacher.TeacherService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationalPlatform {
    private CourseService courseService;
    private EnrollmentService enrollmentService;
    private TeacherService teacherService;
    private StudentService studentService;

   /*
    public EducationalPlatform(){
        this.courseService = new CourseService();
        this.enrollmentService = new EnrollmentService();
        this.teacherService = new TeacherService();
        this.studentService = new StudentService(enrollmentService);
    }
    */


}

