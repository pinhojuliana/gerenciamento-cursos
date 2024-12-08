package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.exceptions.NoUpdateNeededException;
import com.juliana.gerenciamento_cursos.modules.enrollment.dto.EnrollmentDTO;
import com.juliana.gerenciamento_cursos.modules.enrollment.dto.EnrollmentRequestPayload;
import com.juliana.gerenciamento_cursos.modules.enrollment.dto.EnrollmentResponse;
import com.juliana.gerenciamento_cursos.modules.client.entity.EducationalLevel;
import com.juliana.gerenciamento_cursos.modules.client.entity.Student;
import com.juliana.gerenciamento_cursos.modules.course.entity.Course;
import com.juliana.gerenciamento_cursos.modules.enrollment.entity.Enrollment;
import com.juliana.gerenciamento_cursos.exceptions.EmptyListException;
import com.juliana.gerenciamento_cursos.modules.course.repository.CourseRepository;
import com.juliana.gerenciamento_cursos.modules.enrollment.repository.EnrollmentRepository;
import com.juliana.gerenciamento_cursos.modules.client.repository.StudentRepository;
import com.juliana.gerenciamento_cursos.modules.enrollment.service.EnrollmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

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

        Exception thrown = assertThrows(NoSuchElementException.class, () -> {
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

        Exception thrown = assertThrows(NoSuchElementException.class, () -> {
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

        Exception thrown = assertThrows(NoUpdateNeededException.class, () -> {
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
                LocalDate.of(2000, 5, 6), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        Student student1 = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
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

    @Test
    @DisplayName("Deve retornar lista de inscrições em um curso")
    void showEnrollmentsCourseCase1() {
        Student student = new Student(
                "Maria Eduarda", "dudam_", "duda@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        Student student1 = new Student(
                "Ana Maria", "ana.maria5", "anaa2@gmail.com", "P@ssw0rd!",
                LocalDate.of(1995, 12, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        Course course = new Course("Java", "Curso de java");

        Enrollment enrollment = new Enrollment(course, student);
        Enrollment enrollment1 = new Enrollment(course, student1);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        enrollments.add(enrollment1);

        List<EnrollmentDTO> enrollmentsConverted = enrollments.stream()
                .filter(e -> e.getCourse().equals(course))
                .map(e -> new EnrollmentDTO(e.getCourse().getTitle(),
                        e.getStudent().getUsername(),
                        e.getEnrollmentDateTime(),
                        e.getDeadlineForCompletion(),
                        e.getDuration(),
                        e.isActive()))
                .toList();

        when(repository.findByCourseId(any())).thenReturn(Optional.of(enrollments));
        List<EnrollmentDTO> result = service.showCourseEnrollments(any());

        assertEquals(enrollmentsConverted, result);
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void showEnrollmentsCourseCase2() {
        when(repository.findByCourseId(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(NoSuchElementException.class, () -> {
            service.showCourseEnrollments(UUID.randomUUID());
        });

        assertEquals("Curso não encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de instudantes com inscrição ativa")
    void showStudentsEnrollmentsActiveCase1() {
        Student student = new Student(
                "Maria Eduarda", "dudam_", "duda@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        Student student1 = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        Course course = new Course("Java", "Curso de java");

        Enrollment enrollment = new Enrollment(course, student);
        Enrollment enrollment1 = new Enrollment(course, student1);

        enrollment1.setActive(false);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        enrollments.add(enrollment1);

        List<EnrollmentDTO> enrollmentsConverted = enrollments.stream()
                .filter(Enrollment::isActive)
                .map(e -> new EnrollmentDTO(e.getCourse().getTitle(),
                        e.getStudent().getUsername(),
                        e.getEnrollmentDateTime(),
                        e.getDeadlineForCompletion(),
                        e.getDuration(),
                        true))
                .toList();

        when(repository.findByCourseId(any())).thenReturn(Optional.of(enrollments));
        List<EnrollmentDTO> result = service.showCourseActiveEnrollments(any());

        assertEquals(enrollmentsConverted, result);
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void showStudentsEnrollmentsActiveCase2() {
        when(repository.findByCourseId(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(EmptyListException.class, () -> {
            service.showCourseActiveEnrollments(UUID.randomUUID());
        });

        assertEquals("Nenhuma inscrição encontrada para esse curso", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção lista de ativas vazia")
    void showStudentsEnrollmentsActiveCase3() {
        Student student = new Student(
                "Maria Eduarda", "dudam_", "duda@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        Student student1 = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        Course course = new Course("Java", "Curso de java");

        Enrollment enrollment = new Enrollment(course, student);
        Enrollment enrollment1 = new Enrollment(course, student1);

        enrollment.setActive(false);
        enrollment1.setActive(false);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        enrollments.add(enrollment1);

        when(repository.findByCourseId(any())).thenReturn(Optional.of(enrollments));

        Exception thrown = assertThrows(EmptyListException.class, () -> {
            service.showCourseActiveEnrollments(any());
        });

        assertEquals("Não há inscrições ativas para esse curso", thrown.getMessage());

    }

    @Test
    @DisplayName("Deve retornar lista de inscrições de um estudante em cursos")
    void showEnrollmentsStudentCase1() {
        Student student = new Student(
                "Maria Eduarda", "dudam_", "duda@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        Course course = new Course("Java", "Curso de java");
        Course course1 = new Course("Phyton", "curso de Phyton");
        Course course2 = new Course("Java Script", "Curso de Java Script");

        Enrollment enrollment = new Enrollment(course, student);
        Enrollment enrollment1 = new Enrollment(course1, student);
        Enrollment enrollment2 = new Enrollment(course2, student);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);

        List<EnrollmentDTO> enrollmentsConverted = enrollments.stream()
                .filter(e -> e.getStudent().equals(student))
                .map(e -> new EnrollmentDTO(e.getCourse().getTitle(),
                        e.getStudent().getUsername(),
                        e.getEnrollmentDateTime(),
                        e.getDeadlineForCompletion(),
                        e.getDuration(),
                        e.isActive()))
                .toList();

        when(repository.findByStudentId(any())).thenReturn(Optional.of(enrollments));
        List<EnrollmentDTO> result = service.showStudentEnrollments(student.getId());

        assertEquals(enrollmentsConverted, result);
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void showEnrollmentsStudentCase3() {
        when(repository.findByStudentId(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(NoSuchElementException.class, () -> {
            service.showStudentEnrollments(UUID.randomUUID());
        });

        assertEquals("Estudante não encontrado", thrown.getMessage());

    }

    @Test
    @DisplayName("Deve realizar operação com sucesso")
    void unsubscribeStudentOfCourseCase1() {
        Student student = new Student(
                "Maria Eduarda", "dudam_", "duda@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );
        Course course = new Course("Java", "Curso de java");
        Enrollment enrollment = new Enrollment(course, student);

        when(repository.findByCourseIdAndStudentId(any(), any())).thenReturn(Optional.of(enrollment));
        service.unsubscribeStudentOfCourse(new EnrollmentRequestPayload(UUID.randomUUID(), UUID.randomUUID()));
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção inscrição inexistente")
    void unsubscribeStudentOfCourseCase2() {
        when(repository.findByCourseIdAndStudentId(any(), any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(NoSuchElementException.class, () -> {
            service.unsubscribeStudentOfCourse(new EnrollmentRequestPayload(UUID.randomUUID(), UUID.randomUUID()));
        });

        assertEquals("Inscrição não encontrada", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção NoUpdateNeededException")
    void unsubscribeStudentOfCourseCase3() {
        Enrollment enrollment = new Enrollment(new Course(), new Student());
        enrollment.setActive(false);

        when(repository.findByCourseIdAndStudentId(any(), any())).thenReturn(Optional.of(enrollment));

        Exception thrown = assertThrows(NoUpdateNeededException.class, () -> {
            service.unsubscribeStudentOfCourse(new EnrollmentRequestPayload(UUID.randomUUID(), UUID.randomUUID()));
        });

        assertEquals("A inscrição já está inativa", thrown.getMessage());
        verify(repository, never()).save(any());
    }

}