package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.repository.CourseRepository;
import com.juliana.gerenciamento_cursos.repository.EnrollmentRepository;
import com.juliana.gerenciamento_cursos.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

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
    }

    @Test
    @DisplayName("Deve lançar exceção curso não existente")
    void enrollStudentInCourseCase2() {
    }

    @Test
    @DisplayName("Deve lançar exceção estudante não existente")
    void enrollStudentInCourseCase3() {
    }

    @Test
    @DisplayName("Deve lançar exceção inscrição não existente")
    void enrollStudentInCourseCase4() {
    }

    @Test
    @DisplayName("Deve retornar lista de inscrições")
    void showAllEnrollmentsCase1() {
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void showAllEnrollmentsCase2() {
    }

    @Test
    @DisplayName("Deve retornar lista de inscrições em um curso")
    void showEnrollmentsCourseCase1() {
    }

    @Test
    @DisplayName("Deve lançar exceção curso nao existente")
    void showEnrollmentsCourseCase2() {
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void showEnrollmentsCourseCase3() {
    }

    @Test
    @DisplayName("Deve retornar lista de inscrições de um estudante em um curso")
    void showEnrollmentsStudentCase1() {
    }

    @Test
    @DisplayName("Deve lançar exceção aluno nao existente")
    void showEnrollmentsStudentCase2() {
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void showEnrollmentsStudentCase3() {
    }

    @Test
    @DisplayName("Deve retornar lista de instudantes com inscrição ativa")
    void showStudentsEnrollmentsActiveCase1() {
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void showStudentsEnrollmentsActiveCase2() {
    }

    @Test
    @DisplayName("Deve realizar operação com sucesso")
    void unsubscribeStudentOfCourseCase1() {
    }

    @Test
    @DisplayName("Deve lançar exceção inscrição inexistente")
    void unsubscribeStudentOfCourseCase2() {
    }

    @Test
    @DisplayName("Deve lançar exceção NoUpdateRequiredException")
    void unsubscribeStudentOfCourseCase3() {
    }


}