package com.juliana.gerenciamento_cursos.modules.enrollment.service;

import com.juliana.gerenciamento_cursos.modules.enrollment.dto.EnrollmentDTO;
import com.juliana.gerenciamento_cursos.modules.enrollment.dto.EnrollmentRequestPayload;
import com.juliana.gerenciamento_cursos.modules.course.entity.Course;
import com.juliana.gerenciamento_cursos.modules.enrollment.entity.Enrollment;
import com.juliana.gerenciamento_cursos.modules.enrollment.dto.EnrollmentResponse;
import com.juliana.gerenciamento_cursos.modules.client.entity.Student;
import com.juliana.gerenciamento_cursos.exceptions.NoUpdateNeededException;
import com.juliana.gerenciamento_cursos.modules.course.repository.CourseRepository;
import com.juliana.gerenciamento_cursos.modules.enrollment.repository.EnrollmentRepository;
import com.juliana.gerenciamento_cursos.modules.client.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository repository;

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    public EnrollmentResponse enrollStudentInCourse(EnrollmentRequestPayload requestPayload) throws NoSuchElementException, NoUpdateNeededException {
        Course course = courseRepository.findById(requestPayload.courseId()).orElseThrow(()-> new NoSuchElementException("Curso não encontrado"));
        Student student = studentRepository.findById(requestPayload.studentId()).orElseThrow(()-> new NoSuchElementException("Aluno não encontrado"));

        if (repository.existsByStudentIdAndCourseId(requestPayload.studentId(), requestPayload.courseId())) {
            throw new NoUpdateNeededException("Aluno já inscrito neste curso");
        }

        int duration = 365;
        var enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .active(true)
                .duration(duration)
                .deadlineForCompletion(LocalDateTime.now().plusDays(duration).toLocalDate())
                .build();

        repository.save(enrollment);

        return new EnrollmentResponse(enrollment.getId());
    }

    public List<EnrollmentDTO> showAllEnrollments(){
        List<EnrollmentDTO> enrollments = repository.findAll().stream()
                .map(this::convertToDTO)
                .toList();

        if(enrollments.isEmpty()){
            throw new NoSuchElementException("Nenhuma inscrição encontrada");
        }

        return enrollments;
    }

    public List<EnrollmentDTO> showCourseEnrollments(UUID courseId) {
        List<EnrollmentDTO> enrollments = repository.findByCourseId(courseId)
                .orElseThrow(() -> new NoSuchElementException("Curso não encontrado"))
                .stream()
                .map(this::convertToDTO)
                .toList();

        if(enrollments.isEmpty()){
            throw new NoSuchElementException("Nenhuma inscrição encontrada para esse curso");
        }

        return enrollments;
    }

    public List<EnrollmentDTO> showCourseActiveEnrollments(UUID courseId) {
        List<EnrollmentDTO> activeEnrollments = repository.findByCourseId(courseId)
                .orElseThrow(() -> new NoSuchElementException("Nenhuma inscrição encontrada para esse curso"))
                .stream()
                .filter(Enrollment::isActive)
                .map(this::convertToDTO)
                .toList();

        if(activeEnrollments.isEmpty()){
            throw new NoSuchElementException("Não há inscrições ativas para esse curso");
        }

        return activeEnrollments;
    }

    public List<EnrollmentDTO> showStudentEnrollments(UUID studentId){
        List<EnrollmentDTO> enrollments = repository.findByStudentId(studentId)
                .orElseThrow(() -> new NoSuchElementException("Estudante não encontrado"))
                .stream()
                .map(this::convertToDTO)
                .toList();

        if(enrollments.isEmpty()){
            throw new NoSuchElementException("Nenhuma inscrição encontrada para esse aluno");
        }

        return enrollments;
    }

    public void unsubscribeStudentOfCourse(EnrollmentRequestPayload requestPayload) throws NoUpdateNeededException {
         Enrollment enrollment = repository.findByCourseIdAndStudentId(requestPayload.courseId(), requestPayload.studentId())
                .orElseThrow(() -> new NoSuchElementException("Inscrição não encontrada"));

        if (!enrollment.isActive()) {
            throw new NoUpdateNeededException("A inscrição já está inativa");
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
