package com.juliana.gerenciamento_cursos;

import com.juliana.gerenciamento_cursos.entity.enrollment.EnrollmentRepository;
import com.juliana.gerenciamento_cursos.entity.enrollment.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EnrollmentTest {
    @InjectMocks
    EnrollmentService service;

    @Mock
    EnrollmentRepository repository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }
}
