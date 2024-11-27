package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.modules.course.dto.CourseDTO;
import com.juliana.gerenciamento_cursos.modules.client.dto.TeacherDTO;
import com.juliana.gerenciamento_cursos.modules.course.dto.CourseRequestPayload;
import com.juliana.gerenciamento_cursos.modules.course.dto.CourseResponse;
import com.juliana.gerenciamento_cursos.modules.client.entity.Teacher;
import com.juliana.gerenciamento_cursos.modules.course.entity.Course;
import com.juliana.gerenciamento_cursos.exceptions.EmptyListException;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.exceptions.NoUpdateRequiredException;
import com.juliana.gerenciamento_cursos.exceptions.TitleAlreadyInUseException;
import com.juliana.gerenciamento_cursos.modules.course.repository.CourseRepository;
import com.juliana.gerenciamento_cursos.modules.client.repository.TeacherRepository;
import com.juliana.gerenciamento_cursos.modules.course.service.CourseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    @Mock
    CourseRepository repository;

    @Mock
    TeacherRepository teacherRepository;

    @InjectMocks
    CourseService service;

    @Test
    @DisplayName("Deve criar curso com sucesso")
    void createNewCourseCase1() {
        CourseRequestPayload requestPayload = new CourseRequestPayload("Java", "Faça aplicções utilizando java e Spring boot");

        Course course = new Course(requestPayload.title(),
                requestPayload.description());

        when(repository.existsByTitle(any())).thenReturn(false);
        when(repository.save(any())).thenReturn(course);

        CourseResponse result = service.createNewCourse(requestPayload);

        verify(repository, times(1)).save(any());
        assertEquals(course.getId(), result.id());
    }

    @Test
    @DisplayName("Deve lançar exceção título já utilizado")
    void createNewCourseCase2() {
        CourseRequestPayload requestPayload = new CourseRequestPayload("Java", "Faça aplicções utilizando java e Spring boot");

        when(repository.existsByTitle(any())).thenReturn(true);

        Exception thrown = assertThrows(TitleAlreadyInUseException.class, () -> {
            service.createNewCourse(requestPayload);
        });

        assertEquals("Esse curso já existe", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve apagar curso com sucesso")
    void deleteCourseCase1() {
        UUID id = UUID.randomUUID();

        Course course = new Course("Java", "Faça aplicções utilizando java e Spring boot");

        when(repository.findById(any())).thenReturn(Optional.of(course));

        service.deleteCourse(id);
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void deleteCourseCase2() {
        UUID id = UUID.randomUUID();

        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.deleteCourse(id);
        });

        assertEquals("Esse curso não existe", thrown.getMessage());

        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve alterar descrição com sucesso")
    void alterDescriptionCase1() {
        UUID id = UUID.randomUUID();
        Course course = new Course("Java", "Faça aplicações utilizando java e Spring boot");

        when(repository.findById(any())).thenReturn(Optional.of(course));
        service.alterDescription(id, "Construa aplicações modernas com Java e o framework Spring");

        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro id invalido")
    void alterDescriptionCase2() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.alterDescription(id, "Construa aplicações modernas com Java e o framework Spring");
        });

        assertEquals("Esse curso não existe", thrown.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção campo atual igual ao novo")
    void alterDescriptionCase3() {
        UUID id = UUID.randomUUID();
        Course course = new Course("Java", "Faça aplicações utilizando java e Spring boot");

        when(repository.findById(any())).thenReturn(Optional.of(course));

        Exception thrown = assertThrows(NoUpdateRequiredException.class, () -> {
            service.alterDescription(id, "Faça aplicações utilizando java e Spring boot");
        });

        assertEquals("A nova descrição não pode ser igual à descrição atual", thrown.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve retornar lista de cursos")
    void showAllCoursesCase1() {
        Course course = new Course("Java", "Faça aplicções utilizando java e Spring boot");
        Course course1 = new Course("Python", "Aprenda uma das linguagens mais famosas e versáteis com os melhores do mercado");

        List<Course> courses = new ArrayList<>();
        courses.add(course);
        courses.add(course1);

        List<CourseDTO> coursesConverted = courses.stream()
                .map(c -> new CourseDTO(c.getTitle(), c.getDescription(), c.getCreatedAt()))
                .toList();

        when(repository.findAll()).thenReturn(courses);
        List<CourseDTO> result = service.showAllCourses();

        assertEquals(coursesConverted, result);
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void showAllCoursesCase2() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        Exception thrown = assertThrows(EmptyListException.class, () -> {
            service.showAllCourses();
        });

        assertEquals("Nenhum curso encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de cursos pelo título")
    void findCourseByTitleCase1() {
        Course course = new Course("Java", "Faça aplicções utilizando java e Spring boot");
        CourseDTO courseDTO = new CourseDTO(course.getTitle(), course.getDescription(), course.getCreatedAt());

        when(repository.findByTitle(any())).thenReturn(Optional.of(course));
        CourseDTO result = service.findCourseByTitle("Java");

        assertEquals(courseDTO, result);
    }

    @Test
    @DisplayName("Deve lançar exceção opção inexistente")
    void findCourseByTitleCase2() {
        when(repository.findByTitle(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.findCourseByTitle("java");
        });

        assertEquals("Nenhum curso encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve adicionar professor com sucesso")
    void addTeacherCase1() {
        Course course = new Course("Java", "Faça aplicções utilizando java e Spring boot");
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );

        when(repository.findById(any())).thenReturn(Optional.of(course));
        when(teacherRepository.findById(any())).thenReturn(Optional.of(teacher));

        service.addTeacher(UUID.randomUUID(), UUID.randomUUID());

        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção id invalido (teacher)")
    void addTeacherCase2() {
        when(repository.findById(any())).thenReturn(Optional.of(new Course()));
        when(teacherRepository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.addTeacher(UUID.randomUUID(), UUID.randomUUID());
        });

        assertEquals("Professor não encontrado", thrown.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceçao id invalido (course)")
    void addTeacherCase3() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.addTeacher(UUID.randomUUID(), UUID.randomUUID());
        });

        assertEquals("Esse curso não existe", thrown.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve remover professor com sucesso")
    void removeTeacherCase1() {
        Course course = new Course("Java", "Aprende a fazer aplicações java");
        Teacher teacher = new Teacher("Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1995, 3, 7));
        course.getTeachers().add(teacher);

        when(repository.findById(any())).thenReturn(Optional.of(course));
        when(teacherRepository.findById(any())).thenReturn(Optional.of(teacher));

        service.removeTeacher(UUID.randomUUID(), UUID.randomUUID());

        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção id invalido (teacher)")
    void removeTeacherCase2() {
        when(repository.findById(any())).thenReturn(Optional.of(new Course()));
        when(teacherRepository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.removeTeacher(UUID.randomUUID(), UUID.randomUUID());
        });

        assertEquals("Professor não encontrado", thrown.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceçao id invalido (course)")
    void removeTeacherCase3() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.removeTeacher(UUID.randomUUID(), UUID.randomUUID());
        });

        assertEquals("Esse curso não existe", thrown.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve retornar lista de professores")
    void showTeachersOfCourseCase1() {
        Course course = new Course("Java", "Faça aplicções utilizando java e Spring boot");

        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );

        Teacher teacher1 = new Teacher(
                "Ana Maria", "silva.mr", "anasilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6)
        );

        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher);
        teachers.add(teacher1);

        course.getTeachers().add(teacher);
        course.getTeachers().add(teacher1);

        Set<TeacherDTO> teachersConverted = teachers.stream()
                .map(t ->  new TeacherDTO(
                        t.getName(),
                        t.getUsername(),
                        t.getEmail(),
                        t.getDateOfBirth(),
                        t.getSkills()
                ))
                .collect(Collectors.toSet());

        when(repository.findById(any())).thenReturn(Optional.of(course));
        Set<TeacherDTO> result = service.showTeachersOfCourse(UUID.randomUUID());

        assertEquals(teachersConverted, result);
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void showTeachersOfCourseCase2() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.showTeachersOfCourse(UUID.randomUUID());
        });

        assertEquals("Esse curso não existe", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void showTeachersOfCourseCase3() {
        Course course = new Course("Java", "Faça aplicções utilizando java e Spring boot");
        when(repository.findById(any())).thenReturn(Optional.of(course));

        Exception thrown = assertThrows(EmptyListException.class, () -> {
            service.showTeachersOfCourse(UUID.randomUUID());
        });

        assertEquals("Nada encontrado", thrown.getMessage());
    }
}