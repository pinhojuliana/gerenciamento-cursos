package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.DTOs.EnrollmentDTO;
import com.juliana.gerenciamento_cursos.DTOs.request_payload.EnrollmentRequestPayload;
import com.juliana.gerenciamento_cursos.domain.course.Course;
import com.juliana.gerenciamento_cursos.domain.enrollment.Enrollment;
import com.juliana.gerenciamento_cursos.DTOs.response.EnrollmentResponse;
import com.juliana.gerenciamento_cursos.domain.client.Student;
import com.juliana.gerenciamento_cursos.exceptions.EmptyListException;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.repository.CourseRepository;
import com.juliana.gerenciamento_cursos.repository.EnrollmentRepository;
import com.juliana.gerenciamento_cursos.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository repository;

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    public EnrollmentResponse enrollStudentInCourse(EnrollmentRequestPayload requestPayload){
        Course course = courseRepository.findById(requestPayload.courseId()).orElseThrow(()-> new InexistentOptionException("Curso não encontrado"));
        Student student = studentRepository.findById(requestPayload.studentId()).orElseThrow(()-> new InexistentOptionException("Aluno não encontrado"));

        if (repository.existsByStudentIdAndCourseId(requestPayload.studentId(), requestPayload.courseId())) {
            throw new InexistentOptionException("Aluno já inscrito neste curso");
        }

        Enrollment newEnrollment = new Enrollment(course, student);
        repository.save(newEnrollment);

        return new EnrollmentResponse(newEnrollment.getId());
    }

    public List<EnrollmentDTO> showAllEnrollments(){
        List<Enrollment> enrollments = repository.findAll();
        if(enrollments.isEmpty()){
            throw new EmptyListException("Nenhuma inscrição encontrada");
        }
        return enrollments.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<EnrollmentDTO> showCourseEnrollments(UUID courseId) {
        if (repository.existsByCourseId(courseId)) {
            List<Enrollment> enrollments = repository.
                    findByCourseId(courseId)
                    .orElseThrow(() -> new EmptyListException("Nenhuma inscrição encontrada para esse curso"));

            return enrollments.stream()
                    .map(this::convertToDTO)
                    .toList();
        }
        throw new InexistentOptionException("Esse curso não existe");
    }

    public List<EnrollmentDTO> showStudentEnrollments(UUID studentId){
        if (repository.existsByStudentId(studentId)) {
            List<Enrollment> enrollments = repository.findByStudentId(studentId)
                    .orElseThrow(() -> new EmptyListException("Nenhuma inscrição encontrada para esse aluno"));

            return enrollments.stream()
                    .map(this::convertToDTO)
                    .toList();
        }
        throw new InexistentOptionException("Esse aluno não existe");
    }

    public List<EnrollmentDTO> showStudentsEnrollmentsActive(UUID courseId) {
        List<Enrollment> enrollments = repository.
                findByCourseId(courseId)
                .orElseThrow(() -> new EmptyListException("Nenhuma inscrição encontrada para esse curso"));

        return enrollments.stream()
                .filter(Enrollment::isActive)
                .map(this::convertToDTO)
                .toList();
    }

    public void unsubscribeStudentOfCourse(EnrollmentRequestPayload requestPayload) throws InexistentOptionException {
      Enrollment enrollment = repository.findByStudentIdAndCourseId(requestPayload.studentId(), requestPayload.courseId()).orElseThrow(() -> new InexistentOptionException("Inscrição não encontrada"));

        if (!enrollment.isActive()) {
            throw new InexistentOptionException("A inscrição já está inativa");
        }

      enrollment.setActive(false);
      repository.save(enrollment);

    }

    private EnrollmentDTO convertToDTO(Enrollment enrollment){
        return new EnrollmentDTO(enrollment.getCourse().getTitle(),
                enrollment.getStudent().getUsername(),
                enrollment.getEnrollmentDateTime(),
                enrollment.getDeadlineForCompletion(),
                enrollment.getDuration(),
                enrollment.isActive());
    }
}
