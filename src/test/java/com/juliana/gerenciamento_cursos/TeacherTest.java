package com.juliana.gerenciamento_cursos;

import com.juliana.gerenciamento_cursos.entity.user.UserRequestPayload;
import com.juliana.gerenciamento_cursos.entity.user.UserResponse;
import com.juliana.gerenciamento_cursos.entity.teacher.Teacher;
import com.juliana.gerenciamento_cursos.entity.teacher.TeacherRepository;
import com.juliana.gerenciamento_cursos.entity.teacher.TeacherService;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherTest {
    @InjectMocks
    TeacherService service;

    @Mock
    TeacherRepository repository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarUsuario(){
        Teacher teacher = new Teacher("Maria", "maria.s1", "silva.maria11@gmail.com", "Maria#123", LocalDate.of(1999, 5, 7));
        teacher.setId(UUID.randomUUID());
        UserRequestPayload requestPayload = new UserRequestPayload("Maria", "maria.s1", "silva.maria11@gmail.com", "Maria#123", LocalDate.of(1999, 5, 7));

        when(repository.findByUsername("maria.s1")).thenReturn(Optional.empty());
        when(repository.save(any(Teacher.class))).thenReturn(teacher);

        UserResponse response = service.createNewTeacher(requestPayload);

        assertNotNull(response);
        assertEquals(teacher.getId(), response.id());
    }

    @Test
    void deveLancarExcecaoCampoUsernameJaExistente(){
        Teacher teacher = new Teacher("Maria", "maria.s1", "silva.maria11@gmail.com", "Maria#123", LocalDate.of(1999, 5, 7));
        UserRequestPayload requestPayload = new UserRequestPayload("Maria Eduarda", "maria.s1", "dudam@gmail.com", "Maria#123", LocalDate.of(2004, 8, 9));

        when(repository.findByUsername("maria.s1")).thenReturn(Optional.of(teacher));

        assertThrows(Exception.class, () -> {
            service.createNewTeacher(requestPayload);
        });
    }

    @Test
    void deveLancarExcecaoCampoUsernameInvalido(){
        UserRequestPayload requestPayload = new UserRequestPayload("João Paulo", "jp)", "jpaulo@gmail.com", "pPjo#123", LocalDate.of(2003, 9, 9));
        UserRequestPayload requestPayload2 = new UserRequestPayload("João Pedro", "joao pedro", "joao@outlook.com", "pPjo#123", LocalDate.of(2003, 9, 9));

        assertThrows(Exception.class, () -> {
            service.createNewTeacher(requestPayload);
        });

        assertThrows(Exception.class, () -> {
            service.createNewTeacher(requestPayload2);
        });

    }

    @Test
    void deveLancarExcecaoCampoEmailRepetido(){
        Teacher teacher1 = new Teacher("Maria", "maria.s1", "silva.maria11@gmail.com", "Maria#123", LocalDate.of(1999, 5, 7));

        UserRequestPayload requestPayload = new UserRequestPayload("Maria Eduarda", "maria.s1", "dudam@gmail.com", "Maria#123", LocalDate.of(2004, 8, 9));

        when(repository.findByUsername("maria.s1")).thenReturn(Optional.of(teacher1));

        assertThrows(Exception.class, () -> {
            service.createNewTeacher(requestPayload);
        });
    }

    @Test
    void deveLancarExcecaoCampoEmailInvalido(){
        UserRequestPayload requestPayload = new UserRequestPayload("Maria Eduarda", "maria.s1", "dudaml.com", "Maria#123", LocalDate.of(2004, 8, 9));

        assertThrows(Exception.class, () -> {
            service.createNewTeacher(requestPayload);
        });
    }

    @Test
    void deveLancarExcecaoCampoPasswordInvalido(){
        UserRequestPayload requestPayload = new UserRequestPayload("Maria Eduarda", "maria.s1", "dudam@gmail.com", "mariasilva", LocalDate.of(2004, 8, 9));
        UserRequestPayload requestPayload2 = new UserRequestPayload("Maria Eduarda", "mariaduda_s", "dudam23@gmail.com", "hj", LocalDate.of(2005, 8, 9));

        assertThrows(Exception.class, () -> {
            service.createNewTeacher(requestPayload);
        });
        assertThrows(Exception.class, () -> {
            service.createNewTeacher(requestPayload2);
        });
    }

    @Test
    void deveLancarExcecaoUnderage(){
        UserRequestPayload requestPayload = new UserRequestPayload("Maria Eduarda", "maria.s1", "dudam@gmail.com", "Maria#123", LocalDate.of(2008, 8, 9));

        assertThrows(UnderageException.class, () -> {
            service.createNewTeacher(requestPayload);
        });
    }

    @Test
    void deveDeletarUsuario(){
        Teacher teacher = new Teacher("Maria", "maria.s1", "silva.maria11@gmail.com", "Maria#123", LocalDate.of(1999, 5, 7));
        teacher.setId(UUID.randomUUID());

        when(repository.findByUsername("maria.s1")).thenReturn(Optional.of(teacher));
        when(repository.findById(teacher.getId())).thenReturn(Optional.of(teacher));
        doNothing().when(repository).deleteById(teacher.getId());

        service.deleteTeacher(teacher.getId());

        verify(repository).deleteById(teacher.getId());
        verify(repository, times(0)).findByUsername("maria.s1");
        verify(repository, times(0)).findById(teacher.getId());
    }
}
