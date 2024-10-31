package com.juliana.gerenciamento_cursos.entity.enrollment;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.student.Student;
import com.juliana.gerenciamento_cursos.exceptions.EmptyListException;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
public class EnrollmentService {
    // ver se tem como implementar dry
    @Autowired
    EnrollmentRepository repository;

    public EnrollmentResponse enrollStudentInCourse(Course course, Student student){
        Enrollment newEnrollment = new Enrollment(course, student);
        repository.save(newEnrollment);

        return new EnrollmentResponse(newEnrollment.getId());
    }

    public List<Enrollment> showAllEnrollments(){
        List<Enrollment> enrollments = repository.findAll();
        if(enrollments.isEmpty()){
            throw new EmptyListException("Nenhuma inscrição encontrada");
        }
        return enrollments;
    }

    public List<Enrollment> showEnrollmentsCourse(UUID courseId) {
        if (repository.existsByCourseId(courseId)) {
            return repository.
                    findByCourseId(courseId)
                    .orElseThrow(() -> new EmptyListException("Nenhuma inscrição encontrada para esse curso"));
        }
        throw new InexistentOptionException("Esse curso não existe");
    }

    public List<Enrollment> showEnrollmentsStudent(UUID studentId){
        if (repository.existsByStudentId(studentId)) {
            return repository.findByStudentId(studentId)
                    .orElseThrow(() -> new EmptyListException("Nenhuma inscrição encontrada para esse aluno"));
        }
        throw new InexistentOptionException("Esse aluno não existe");
    }

    public List<Enrollment> showStudentsActive(UUID courseId) {
        return showEnrollmentsCourse(courseId)
                .stream()
                .filter(Enrollment::isActive)
                .toList();
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
