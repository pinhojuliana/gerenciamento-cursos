package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.modules.course.dto.CourseDTO;
import com.juliana.gerenciamento_cursos.modules.client.dto.TeacherDTO;
import com.juliana.gerenciamento_cursos.modules.client.dto.TeacherRequestPayload;
import com.juliana.gerenciamento_cursos.modules.client.dto.ClientResponse;
import com.juliana.gerenciamento_cursos.modules.client.entity.Teacher;
import com.juliana.gerenciamento_cursos.modules.client.service.TeacherService;
import com.juliana.gerenciamento_cursos.modules.course.entity.Course;
import com.juliana.gerenciamento_cursos.exceptions.*;
import com.juliana.gerenciamento_cursos.modules.client.repository.TeacherRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @Mock
    TeacherRepository repository;

    @InjectMocks
    TeacherService service;

    @Test
    @DisplayName("deve criar novo professor")
    void createNewTeacherCase1() {
        TeacherRequestPayload requestPayload = new TeacherRequestPayload(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1995, 3, 7)
        );

        Teacher teacher = new Teacher(
                requestPayload.name(),
                requestPayload.username(),
                requestPayload.email(),
                requestPayload.password(),
                requestPayload.dateOfBirth()
        );

        when(repository.save(any(Teacher.class))).thenReturn(teacher);

        ClientResponse response = service.createNewTeacher(requestPayload);

        verify(repository, times(1)).save(any(Teacher.class));

        assertEquals(new ClientResponse(teacher.getId()), response);
    }

    @Test
    @DisplayName("deve lançar exceção de menor idade")
    void createNewTeacherCase2() {
        TeacherRequestPayload requestPayload = new TeacherRequestPayload(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(2008, 3, 7)
        );

        Exception thrown = assertThrows(InvalidAgeException.class, ()-> {
            service.createNewTeacher(requestPayload);
        });

        verify(repository, never()).save(any(Teacher.class));

        assertEquals("Você precisa ter no mínimo 18 anos para se inscrever na plataforma", thrown.getMessage());
    }

    @Test
    @DisplayName("deve lançar exceção de email repetido")
    void createNewTeacherCase3() {

        TeacherRequestPayload requestPayload = new TeacherRequestPayload(
                "Maria Eduarda", "dudam_", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6)
        );

        when(repository.existsByEmail(requestPayload.email())).thenReturn(true);

        Exception thrown = assertThrows(EmailAlreadyInUseException.class, ()-> {
            service.createNewTeacher(requestPayload);
        });

        assertEquals("Esse e-mail já está sendo utilizado por outro usuário", thrown.getMessage());
        verify(repository, never()).save(any(Teacher.class));
    }

    @Test
    @DisplayName("deve lançar exceção de username repetido")
    void createNewTeacherCase4() {
        TeacherRequestPayload requestPayload = new TeacherRequestPayload(
                "Maria Eduarda", "mmaria12", "mariaedu@outlook.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6)
        );

        when(repository.existsByUsername(requestPayload.username())).thenReturn(true);

        Exception thrown = assertThrows(UsernameAlreadyInUseException.class, ()-> {
            service.createNewTeacher(requestPayload);
        });

        assertEquals("Esse username já está sendo utilizado por outro usuário", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar professor com sucesso")
    void deleteTeacherCase1() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );
        when(repository.findById(any())).thenReturn(Optional.of(teacher));
        service.deleteTeacher(any());
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("Deve lançar exceção id invalido")
    void deleteTeacherCase2() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(NoSuchElementException.class, ()-> {
            service.deleteTeacher(id);
        });

        verify(repository, times(1)).findById(any(UUID.class));
        assertEquals("Esse usuário não existe", thrown.getMessage());
        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve salvar uma nova skill com sucesso")
    void addSkillCase1() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );

        when(repository.findById(any())).thenReturn(Optional.of(teacher));
        service.addSkill(any(), "java");
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void addSkillCase2() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(NoSuchElementException.class, () -> {
            service.addSkill(id, "java");
        });

        verify(repository, never()).save(any());
        assertEquals("Esse usuário não existe", thrown.getMessage());

    }

    @Test
    @DisplayName("Deve lançar exceção skill já existente")
    void addSkillCase3() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );

        teacher.getSkills().add("java");

        when(repository.findById(any())).thenReturn(Optional.of(teacher));

        Exception thrown = assertThrows(SkillAlreadyExistsException.class, () -> {
            service.addSkill(any(), "java");
        });

        assertEquals("A skill 'java' já existe neste perfil", thrown.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve remover uma nova skill com sucesso")
    void removeSkillCase1() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );
        teacher.getSkills().add("java");

        when(repository.findById(any())).thenReturn(Optional.of(teacher));
        service.removeSkill(any(), "java");

        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void removeSkillCase2() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(NoSuchElementException.class, () -> {
            service.removeSkill(id, "java");
        });

        verify(repository, never()).save(any());
        assertEquals("Esse usuário não existe", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção skill não existente")
    void removeSkillCase3() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );

        when(repository.findById(any())).thenReturn(Optional.of(teacher));

        Exception thrown = assertThrows(NoSuchElementException.class, () -> {
            service.removeSkill(any(), "java");
        });

        assertEquals("A skill 'java' não foi encontrada", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar username com sucesso")
    void updateTeacherUsernameCase1() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );
        when(repository.findById(any())).thenReturn(Optional.of(teacher));
        when(repository.existsByUsername(any())).thenReturn(false);

        service.updateTeacherUsername(any(), "maria.sv");
        verify(repository, times(1)).save(teacher);
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void updateTeacherUsernameCase2() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(NoSuchElementException.class, ()-> {
            service.updateTeacherUsername(id, "maria.sv");
        });

        verify(repository, times(1)).findById(any(UUID.class));
        assertEquals("Esse usuário não existe", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção de campos novo e atual iguais")
    void updateTeacherUsernameCase3() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );
        when(repository.findById(any())).thenReturn(Optional.of(teacher));

        Exception thrown = assertThrows(NoUpdateNeededException.class, ()-> {
            service.updateTeacherUsername(any(), "mmaria12");
        });

        assertEquals("Os campos 'novo' e 'atual' não devem ser iguais", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção username já utilizado")
    void updateTeacherUsernameCase4() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );

        when(repository.findById(any())).thenReturn(Optional.of(teacher));
        when(repository.existsByUsername(any())).thenReturn(true);

        Exception thrown = assertThrows(UsernameAlreadyInUseException.class, ()-> {
            service.updateTeacherUsername(any(), "maria.sv2");
        });

        assertEquals("Esse username já está sendo utilizado por outro usuário", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar email com sucesso")
    void updateTeacherEmailCase1() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );
        when(repository.findById(any())).thenReturn(Optional.of(teacher));
        when(repository.existsByEmail(any())).thenReturn(false);
        service.updateTeacherEmail(any(), "svmaria@gmail.com");
        verify(repository, times(1)).save(teacher);
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void updateTeacherEmailCase2() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any())).thenThrow(new NoSuchElementException("Esse usuário não existe"));

        Exception thrown = assertThrows(NoSuchElementException.class, ()-> {
            service.updateTeacherEmail(id, "maria@outlook.com");
        });

        verify(repository, times(1)).findById(any(UUID.class));
        assertEquals("Esse usuário não existe", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção de campos novo e atual iguais")
    void updateTeacherEmailCase3() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );
        when(repository.findById(any())).thenReturn(Optional.of(teacher));

        Exception thrown = assertThrows(NoUpdateNeededException.class, ()-> {
            service.updateTeacherEmail(any(), "msilva@gmail.com");
        });

        assertEquals("Os campos 'novo' e 'atual' não devem ser iguais", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção email já utilizado")
    void updateTeacherEmailCase4() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );

        when(repository.findById(any())).thenReturn(Optional.of(teacher));
        when(repository.existsByEmail(any())).thenReturn(true);

        Exception thrown = assertThrows(EmailAlreadyInUseException.class, ()-> {
            service.updateTeacherEmail(any(), "maria.sv@gmail.com");
        });

        assertEquals("Esse e-mail já está sendo utilizado por outro usuário", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar password com sucesso")
    void updateTeacherPasswordCase1() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );
        when(repository.findById(any())).thenReturn(Optional.of(teacher));
        service.updateTeacherPassword(any(), "P@ssw0rd!", "MyP@ssw0rd1");
        verify(repository, times(1)).save(teacher);
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void updateTeacherPasswordCase2() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(NoSuchElementException.class, ()-> {
            service.updateTeacherPassword(id, "P@ssw0rd!", "MyP@ssw0rd1");
        });

        verify(repository, times(1)).findById(any(UUID.class));
        assertEquals("Esse usuário não existe", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção de campos novo e atual iguais")
    void updateTeacherPasswordCase3() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );
        when(repository.findById(any())).thenReturn(Optional.of(teacher));

        Exception thrown = assertThrows(NoUpdateNeededException.class, ()-> {
            service.updateTeacherPassword(any(), "P@ssw0rd!", "P@ssw0rd!");
        });

        assertEquals("Os campos 'novo' e 'atual' não devem ser iguais", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção senha atual incorreta")
    void updateTeacherPasswordCase4() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );
        when(repository.findById(any())).thenReturn(Optional.of(teacher));
        Exception thrown = assertThrows(InvalidPasswordException.class, ()-> {
            service.updateTeacherPassword(any(), "P@ssw0rd.", "P@ssw0rd#");
        });

        assertEquals("A senha atual está incorreta", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve retornar lista de todos os professores")
    void getAllTeachersCase1() {
        Teacher teacher = new Teacher(
                "Maria Eduarda", "dudam_", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6)
        );

        Teacher teacher2 = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );

        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher);
        teachers.add(teacher2);

        List<TeacherDTO> teachersConverted = teachers.stream().map(t -> new TeacherDTO(
                        t.getName(),
                        t.getUsername(),
                        t.getEmail(),
                        t.getDateOfBirth(),
                        t.getSkills()
                       ))
                .toList();

        when(repository.findAll()).thenReturn(teachers);
        List<TeacherDTO> result = service.getAllTeachers();

        assertEquals(teachersConverted, result);
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void getAllTeachersCase2() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        Exception thrown = assertThrows(EmptyListException.class, () -> {
            service.getAllTeachers();
        });

        assertEquals("Não há professores cadastrados", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve reotrnar lista de professores com o nome")
    void findTeacherCase1() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );

        Teacher teacher2 = new Teacher(
                "Maria", "silva.mr", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6)
        );

        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher);
        teachers.add(teacher2);
        List<TeacherDTO> teachersConverted = teachers.stream()
                .map(t -> new TeacherDTO(
                        t.getName(),
                        t.getUsername(),
                        t.getEmail(),
                        t.getDateOfBirth(),
                        t.getSkills()
                ))
                .toList();

        when(repository.findByName(any())).thenReturn(teachers);
        List<TeacherDTO> result = service.findTeacherByName(any());

        assertEquals(teachersConverted, result);
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void findTeacherCase2() {
        when(repository.findByName(any())).thenReturn(new ArrayList<>());

        Exception thrown = assertThrows(EmptyListException.class, () -> {
            service.findTeacherByName(any());
        });

        assertEquals("Nenhum professor com esse nome foi encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de cursos")
    void showAllCoursesTaughtCase1() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );
        Course course = new Course("Java", "Faça aplicções utilizando java e Spring boot");
        Course course1 = new Course("Python", "Aprenda uma das linguagens mais famosas e versáteis com os melhores do mercado");

        teacher.getCoursesTaught().add(course);
        teacher.getCoursesTaught().add(course1);

        Set<Course> courses = new HashSet<>();
        courses.add(course);
        courses.add(course1);
        Set<CourseDTO> coursesConverted = courses.stream()
                .map(c -> new CourseDTO(c.getTitle(), c.getDescription(), c.getCreatedAt()))
                .collect(Collectors.toSet());

        when(repository.findById(any())).thenReturn(Optional.of(teacher));
        Set<CourseDTO> result = service.showAllCoursesTaught(UUID.randomUUID());
        assertEquals(coursesConverted, result);
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void showAllCoursesTaughtCase2() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            service.showAllCoursesTaught(UUID.randomUUID());
        });
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void showAllCoursesTaughtCase3() {
        Teacher teacher = new Teacher(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7)
        );

        when(repository.findById(any())).thenReturn(Optional.of(teacher));

        Exception thrown = assertThrows(EmptyListException.class, () ->{
            service.showAllCoursesTaught(any());
        });

        assertEquals("Não há cursos ministrados.", thrown.getMessage());
    }

}