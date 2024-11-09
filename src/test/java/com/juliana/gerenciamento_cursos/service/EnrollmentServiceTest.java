package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.DTOs.EnrollmentDTO;
import com.juliana.gerenciamento_cursos.DTOs.request_payload.EnrollmentRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.response.EnrollmentResponse;
import com.juliana.gerenciamento_cursos.domain.client.EducationalLevel;
import com.juliana.gerenciamento_cursos.domain.client.Student;
import com.juliana.gerenciamento_cursos.domain.course.Course;
import com.juliana.gerenciamento_cursos.domain.enrollment.Enrollment;
import com.juliana.gerenciamento_cursos.exceptions.EmptyListException;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.exceptions.NoUpdateRequiredException;
import com.juliana.gerenciamento_cursos.repository.CourseRepository;
import com.juliana.gerenciamento_cursos.repository.EnrollmentRepository;
import com.juliana.gerenciamento_cursos.repository.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {
    @Mock
    EnrollmentRepository repository;

    @Mock
    CourseRepository courseRepository;

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    EnrollmentService service;

    @Test
    @DisplayName("Deve inscrever estudante com sucesso")
    void enrollStudentInCourseCase1() {
        Student student = new Student();
        Course course = new Course();
        EnrollmentRequestPayload requestPayload = new EnrollmentRequestPayload(course.getId(), student.getId());
        Enrollment enrollment = new Enrollment(course, student);

        when(courseRepository.findById(any())).thenReturn(Optional.of(course));
        when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        when(repository.existsByStudentIdAndCourseId(any(), any())).thenReturn(false);
        when(repository.save(any())).thenReturn(enrollment);

        EnrollmentResponse result = service.enrollStudentInCourse(requestPayload);

        assertEquals(enrollment.getId(), result.id());
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção curso não existente")
    void enrollStudentInCourseCase2() {
        when(courseRepository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.enrollStudentInCourse(new EnrollmentRequestPayload(UUID.randomUUID(), UUID.randomUUID()));
        });

        assertEquals("Curso não encontrado", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção estudante não existente")
    void enrollStudentInCourseCase3() {
        when(courseRepository.findById(any())).thenReturn(Optional.of(new Course()));
        when(studentRepository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.enrollStudentInCourse(new EnrollmentRequestPayload(UUID.randomUUID(), UUID.randomUUID()));
        });

        assertEquals("Aluno não encontrado", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção inscrição já existente")
    void enrollStudentInCourseCase4() {
        when(courseRepository.findById(any())).thenReturn(Optional.of(new Course()));
        when(studentRepository.findById(any())).thenReturn(Optional.of(new Student()));

        when(repository.existsByStudentIdAndCourseId(any(), any())).thenReturn(true);

        Exception thrown = assertThrows(NoUpdateRequiredException.class, () -> {
            service.enrollStudentInCourse(new EnrollmentRequestPayload(UUID.randomUUID(), UUID.randomUUID()));
        });

        assertEquals("Aluno já inscrito neste curso", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve retornar lista de inscrições")
    void showAllEnrollmentsCase1() {
        Student student = new Student(
                "Maria Eduarda", "dudam_", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6), "estudante de java", EducationalLevel.HIGHER
        );

        Student student1 = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );

        Enrollment enrollment = new Enrollment(new Course("Java", "Curso de java"), student);
        Enrollment enrollment1 = new Enrollment(new Course("Pyhton", "curso de phyton"), student1);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        enrollments.add(enrollment1);

        List<EnrollmentDTO> enrollmentsConverted = enrollments.stream()
                .map(e -> new EnrollmentDTO(e.getCourse().getTitle(),
                        e.getStudent().getUsername(),
                        e.getEnrollmentDateTime(),
                        e.getDeadlineForCompletion(),
                        e.getDuration(),
                        e.isActive()))
                .toList();

        when(repository.findAll()).thenReturn(enrollments);
        List<EnrollmentDTO> result = service.showAllEnrollments();

        assertEquals(enrollmentsConverted, result);
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void showAllEnrollmentsCase2() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        Exception thrown = assertThrows(EmptyListException.class, () -> {
            service.showAllEnrollments();
        });

        assertEquals("Nenhuma inscrição encontrada", thrown.getMessage());
    }




    //Terminar
    @Test
    @DisplayName("Deve retornar lista de inscrições em um curso")
    void showEnrollmentsCourseCase1() {
        Student student = new Student(
                "Maria Eduarda", "dudam_", "duda@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6), "estudante de java", EducationalLevel.HIGHER
        );

        Student student1 = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );

        Student student2 = new Student(
                "Ana Maria", "ana.maria5", "anaa2@gmail.com", "P@ssw0rd!",
                LocalDate.of(1995, 12, 7), "estudante de java", EducationalLevel.HIGHER
        );

        Course course = new Course("Java", "Curso de java");
        Course course1 = new Course("Pyhton", "curso de phyton");


        Enrollment enrollment = new Enrollment(course, student);
        Enrollment enrollment1 = new Enrollment(course1, student1);
        Enrollment enrollment2 = new Enrollment(course, student2);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);

        List<EnrollmentDTO> enrollmentsConverted = enrollments.stream()
                .filter(e -> e.getCourse().equals(course))
                .map(e -> new EnrollmentDTO(e.getCourse().getTitle(),
                        e.getStudent().getUsername(),
                        e.getEnrollmentDateTime(),
                        e.getDeadlineForCompletion(),
                        e.getDuration(),
                        e.isActive()))
                .toList();


       // List<EnrollmentDTO> result = service.showCourseEnrollments();

        //assertEquals(enrollmentsConverted, result);
    }

    @Test
    @DisplayName("Deve lançar exceção curso nao existente")
    void showEnrollmentsCourseCase2() {
        //

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.showCourseEnrollments(UUID.randomUUID());
        });

        assertEquals("Curso não encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void showEnrollmentsCourseCase3() {
        //

        Exception thrown = assertThrows(EmptyListException.class, () -> {
            service.showCourseEnrollments(UUID.randomUUID());
        });

        assertEquals("Nenhuma inscrição encontrada para esse curso", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de inscrições de um estudante em cursos")
    void showEnrollmentsStudentCase1() {
        Student student = new Student(
                "Maria Eduarda", "dudam_", "duda@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6), "estudante de java", EducationalLevel.HIGHER
        );

        Student student1 = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );

        Course course = new Course("Java", "Curso de java");
        Course course1 = new Course("Phyton", "curso de Phyton");
        Course course2 = new Course("Java Script", "Curso de Java Script");

        Enrollment enrollment = new Enrollment(course, student);
        Enrollment enrollment1 = new Enrollment(course1, student);
        Enrollment enrollment2 = new Enrollment(course2, student1);
        Enrollment enrollment3 = new Enrollment(course1, student1);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);

        List<EnrollmentDTO> enrollmentsConverted = enrollments.stream()
                .filter(e -> e.getStudent().equals(student))
                .map(e -> new EnrollmentDTO(e.getCourse().getTitle(),
                        e.getStudent().getUsername(),
                        e.getEnrollmentDateTime(),
                        e.getDeadlineForCompletion(),
                        e.getDuration(),
                        e.isActive()))
                .toList();


       //List<EnrollmentDTO> result = service.showStudentEnrollments(student.getId());

       //assertEquals(enrollmentsConverted, result);
    }

    @Test
    @DisplayName("Deve lançar exceção aluno nao existente")
    void showEnrollmentsStudentCase2() {
        //

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.showStudentEnrollments(UUID.randomUUID());
        });

        assertEquals("Aluno não encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void showEnrollmentsStudentCase3() {
        //

        Exception thrown = assertThrows(EmptyListException.class, () -> {
            service.showStudentEnrollments(UUID.randomUUID());
        });

        assertEquals("Nenhuma inscrição encontrada para esse aluno", thrown.getMessage());

    }

    @Test
    @DisplayName("Deve retornar lista de instudantes com inscrição ativa")
    void showStudentsEnrollmentsActiveCase1() {
        Student student = new Student(
                "Maria Eduarda", "dudam_", "duda@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6), "estudante de java", EducationalLevel.HIGHER
        );

        Student student1 = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );

        Student student2 = new Student(
                "Ana Maria", "ana.maria5", "anaa2@gmail.com", "P@ssw0rd!",
                LocalDate.of(1995, 12, 7), "estudante de java", EducationalLevel.HIGHER
        );

        Course course = new Course("Java", "Curso de java");
        course.setId(UUID.randomUUID());
        Course course1 = new Course("Pyhton", "curso de phyton");
        course1.setId(UUID.randomUUID());

        Enrollment enrollment = new Enrollment(course, student);
        Enrollment enrollment1 = new Enrollment(course1, student1);
        Enrollment enrollment2 = new Enrollment(course, student2);

        enrollment2.setActive(false);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);

        List<EnrollmentDTO> enrollmentsConverted = enrollments.stream()
                .filter(e -> e.getCourse().equals(course) && e.isActive())
                .map(e -> new EnrollmentDTO(e.getCourse().getTitle(),
                        e.getStudent().getUsername(),
                        e.getEnrollmentDateTime(),
                        e.getDeadlineForCompletion(),
                        e.getDuration(),
                        true))
                .toList();

       // List<EnrollmentDTO> result = service.showCourseEnrollmentsActive(course.getId());

        //assertEquals(enrollmentsConverted, result);
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void showStudentsEnrollmentsActiveCase2() {
        //

        Exception thrown = assertThrows(EmptyListException.class, () -> {
            service.showCourseEnrollmentsActive(UUID.randomUUID());
        });

        assertEquals("Nenhuma inscrição ativa encontrada para esse curso", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção de curso invalido")
    void showStudentsEnrollmentsActiveCase3(){
        //

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.showCourseEnrollmentsActive(UUID.randomUUID());
        });

        assertEquals("Curso não encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve realizar operação com sucesso")
    void unsubscribeStudentOfCourseCase1() {

        //service.unsubscribeStudentOfCourse(requestPayload);
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção inscrição inexistente")
    void unsubscribeStudentOfCourseCase2() {
        //
        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.unsubscribeStudentOfCourse(new EnrollmentRequestPayload(UUID.randomUUID(), UUID.randomUUID()));
        });

        assertEquals("Inscrição não encontrada", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção NoUpdateRequiredException")
    void unsubscribeStudentOfCourseCase3() {
        Enrollment enrollment = new Enrollment(new Course(), new Student());
        enrollment.setActive(false);

        //

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.unsubscribeStudentOfCourse(new EnrollmentRequestPayload(UUID.randomUUID(), UUID.randomUUID()));
        });

        assertEquals("A inscrição já está inativa", thrown.getMessage());
        verify(repository, never()).save(any());
    }

}