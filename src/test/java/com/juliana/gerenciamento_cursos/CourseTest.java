package com.juliana.gerenciamento_cursos;

import com.juliana.gerenciamento_cursos.entity.course.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseTest {
    @InjectMocks
    CourseService service;

    @Mock
    CourseRepository repository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarCurso(){
        Course course = new Course("Java", "Curso de java com Spring para iniciantes");
        course.setId(UUID.randomUUID());
        CourseRequestPayload requestPayload = new CourseRequestPayload("Java", "Curso de java com Spring para iniciantes");

        when(repository.findByTitle("Java")).thenReturn(Optional.empty());
        when(repository.save(any(Course.class))).thenReturn(course);

        CourseResponse response = service.createNewCourse(requestPayload);

        assertNotNull(response);
        assertEquals(course.getId(), response.id());
    }

    @Test
    void deveLancarExcecaoCursoJaExistente(){
        Course course = new Course("Java", "Curso de java com Spring para iniciantes");
        CourseRequestPayload requestPayload = new CourseRequestPayload("Java", "Curso de java com Spring para iniciantes");

        when(repository.findByTitle("Java")).thenReturn(Optional.of(course));

        assertThrows(Exception.class, () -> {
            service.createNewCourse(requestPayload);
        });
    }

    @Test
    void deveDeletarCurso(){
        Course course = new Course("Java", "Curso de java com Spring para iniciantes");
        course.setId(UUID.randomUUID());

        when(repository.findByTitle("Java")).thenReturn(Optional.of(course));
        when(repository.findById(course.getId())).thenReturn(Optional.of(course));
        doNothing().when(repository).deleteById(course.getId());

        service.deleteCourse(course.getId());

        verify(repository).deleteById(course.getId());
        verify(repository, times(0)).findByTitle("Java");
        verify(repository, times(0)).findById(course.getId());
    }

}
