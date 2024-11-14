package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.DTOs.EnrollmentDTO;
import com.juliana.gerenciamento_cursos.DTOs.request_payload.EnrollmentRequestPayload;
import com.juliana.gerenciamento_cursos.domain.course.Course;
import com.juliana.gerenciamento_cursos.domain.enrollment.Enrollment;
import com.juliana.gerenciamento_cursos.DTOs.response.EnrollmentResponse;
import com.juliana.gerenciamento_cursos.domain.client.Student;
import com.juliana.gerenciamento_cursos.exceptions.EmptyListException;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.exceptions.NoUpdateRequiredException;
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

    public EnrollmentResponse enrollStudentInCourse(EnrollmentRequestPayload requestPayload) throws InexistentOptionException, NoUpdateRequiredException{
        Course course = courseRepository.findById(requestPayload.courseId()).orElseThrow(()-> new InexistentOptionException("Curso não encontrado"));
        Student student = studentRepository.findById(requestPayload.studentId()).orElseThrow(()-> new InexistentOptionException("Aluno não encontrado"));

        if (repository.existsByStudentIdAndCourseId(requestPayload.studentId(), requestPayload.courseId())) {
            throw new NoUpdateRequiredException("Aluno já inscrito neste curso");
        }

        Enrollment newEnrollment = new Enrollment(course, student);
        repository.save(newEnrollment);

        return new EnrollmentResponse(newEnrollment.getId());
    }

    public List<EnrollmentDTO> showAllEnrollments(){
        List<EnrollmentDTO> enrollments = repository.findAll().stream()
                .map(this::convertToDTO)
                .toList();

        if(enrollments.isEmpty()){
            throw new EmptyListException("Nenhuma inscrição encontrada");
        }

        return enrollments;
    }

    public List<EnrollmentDTO> showCourseEnrollments(UUID courseId) {
        List<EnrollmentDTO> enrollments = repository.findByCourseId(courseId)
                .orElseThrow(() -> new InexistentOptionException("Curso não encontrado"))
                .stream()
                .map(this::convertToDTO)
                .toList();

        if(enrollments.isEmpty()){
            throw new EmptyListException("Nenhuma inscrição encontrada para esse curso");
        }

        return enrollments;
    }

    public List<EnrollmentDTO> showCourseActiveEnrollments(UUID courseId) {
        List<EnrollmentDTO> activeEnrollments = repository.findByCourseId(courseId)
                .orElseThrow(() -> new EmptyListException("Nenhuma inscrição encontrada para esse curso"))
                .stream()
                .filter(Enrollment::isActive)
                .map(this::convertToDTO)
                .toList();

        if(activeEnrollments.isEmpty()){
            throw new EmptyListException("Não há inscrições ativas para esse curso");
        }

        return activeEnrollments;
    }

    public List<EnrollmentDTO> showStudentEnrollments(UUID studentId){
        List<EnrollmentDTO> enrollments = repository.findByStudentId(studentId)
                .orElseThrow(() -> new InexistentOptionException("Estudante não encontrado"))
                .stream()
                .map(this::convertToDTO)
                .toList();

        if(enrollments.isEmpty()){
            throw new EmptyListException("Nenhuma inscrição encontrada para esse aluno");
        }

        return enrollments;
    }

    public void unsubscribeStudentOfCourse(EnrollmentRequestPayload requestPayload) throws NoUpdateRequiredException {
         Enrollment enrollment = repository.findByCourseIdAndStudentId(requestPayload.courseId(), requestPayload.studentId())
                .orElseThrow(() -> new InexistentOptionException("Inscrição não encontrada"));

        if (!enrollment.isActive()) {
            throw new NoUpdateRequiredException("A inscrição já está inativa");
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
