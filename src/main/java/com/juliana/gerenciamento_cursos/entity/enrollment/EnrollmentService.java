package com.juliana.gerenciamento_cursos.entity.enrollment;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.user.student.Student;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
public class EnrollmentService {
    @Autowired
    EnrollmentRepository enrollmentRepository;

    public void showEnrollments(Course course){

    }

    public void showEnrollments(Student student){

    }

    public void showAllEnrollments(){

    }

    public EnrollmentResponse enrollStudentInCourse(EnrollmentRequestPayload enrollmentRequestPayload){
        Enrollment newEnrollment = new Enrollment(enrollmentRequestPayload.course(), enrollmentRequestPayload.student());
        enrollmentRepository.save(newEnrollment);

        return new EnrollmentResponse(newEnrollment.getId());
    }

    //criar condição que quando uma inscrição é colocada como inativa
    //ela nao pode ser colocada como ativa novamente
    public void unsubscribleStudentOfCourse(Student student, Course course){

    }

    //criar metodo para desativar automaticamente apos a data

}
