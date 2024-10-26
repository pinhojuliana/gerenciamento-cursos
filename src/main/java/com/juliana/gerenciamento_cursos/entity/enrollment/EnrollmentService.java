package com.juliana.gerenciamento_cursos.entity.enrollment;

import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
public class EnrollmentService {
    @Autowired
    EnrollmentRepository repository;

    public List<Enrollment> showEnrollmentsCourse(UUID courseId){
        return repository.findByCourseId(courseId);
    }

    public List<Enrollment> showStudentsActive(UUID courseId) {
        return repository.findByCourseId(courseId).stream()
                .filter(Enrollment::isActive)
                .toList();
    }

    public List<Enrollment> showEnrollmentsStudent(UUID studentId){
        return repository.findByStudentId(studentId);
    }

    public List<Enrollment> showAllEnrollments(){
        return repository.findAll();
    }

    public EnrollmentResponse enrollStudentInCourse(EnrollmentRequestPayload enrollmentRequestPayload){
        Enrollment newEnrollment = new Enrollment(enrollmentRequestPayload.course(), enrollmentRequestPayload.student());
        repository.save(newEnrollment);

        return new EnrollmentResponse(newEnrollment.getId());
    }

    public void unsubscribleStudentOfCourse(UUID studentId, UUID courseId) throws InexistentOptionException {
      Enrollment enrollment = repository.findFirstByStudentId(studentId).orElseThrow(() -> new InexistentOptionException("Usuario não encontrado"));
        if (enrollment.getCourse().getId() == courseId){
            enrollment.setActive(false);
            repository.save(enrollment);
        } else {
            throw new InexistentOptionException("Inscrição ou curso inexistente");
        }
    }

}
