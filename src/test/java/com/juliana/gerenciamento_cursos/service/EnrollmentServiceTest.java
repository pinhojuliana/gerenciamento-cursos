package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.repository.CourseRepository;
import com.juliana.gerenciamento_cursos.repository.EnrollmentRepository;
import com.juliana.gerenciamento_cursos.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
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
    void enrollStudentInCourse() {
    }

    @Test
    void showAllEnrollments() {
    }

    @Test
    void showEnrollmentsCourse() {
    }

    @Test
    void showEnrollmentsStudent() {
    }

    @Test
    void showStudentsEnrollmentsActive() {
    }

    @Test
    void unsubscribeStudentOfCourse() {
    }
}