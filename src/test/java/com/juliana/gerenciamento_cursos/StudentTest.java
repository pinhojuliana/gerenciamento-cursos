package com.juliana.gerenciamento_cursos;

import com.juliana.gerenciamento_cursos.entity.user.UserRequestPayload;
import com.juliana.gerenciamento_cursos.entity.user.UserResponse;
import com.juliana.gerenciamento_cursos.entity.user.student.EducationalLevel;
import com.juliana.gerenciamento_cursos.entity.user.student.Student;
import com.juliana.gerenciamento_cursos.entity.user.student.StudentRepository;
import com.juliana.gerenciamento_cursos.entity.user.student.StudentService;
import static org.junit.jupiter.api.Assertions.*;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentTest {
    @InjectMocks
    StudentService service;

    @Mock
    StudentRepository repository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarUsuario(){
        Student student = new Student("Maria", "maria.s1", "silva.maria11@gmail.com", "Maria#123", LocalDate.of(1999, 5, 7),"Estudante de programação", EducationalLevel.HIGHER);
        student.setId(UUID.randomUUID());
        UserRequestPayload requestPayload = new UserRequestPayload("Maria", "maria.s1", "silva.maria11@gmail.com", "Maria#123", LocalDate.of(1999, 5, 7));

        when(repository.findByUsername("maria.s1")).thenReturn(Optional.empty());
        when(repository.save(any(Student.class))).thenReturn(student);

        UserResponse response = service.createNewStudent(requestPayload, "Estudante de programação", EducationalLevel.HIGHER);

        assertNotNull(response);
        assertEquals(student.getId(), response.id());
    }

    @Test
    void deveLancarExcecaoCampoUsernameJaExistente(){
        Student student = new Student("Maria", "maria.s1", "silva.maria11@gmail.com", "Maria#123", LocalDate.of(1999, 5, 7), "Engenheira de Software", EducationalLevel.MASTERS_DEGREE);

        UserRequestPayload requestPayload = new UserRequestPayload("Maria Eduarda", "maria.s1", "dudam@gmail.com", "Maria#123", LocalDate.of(2004, 8, 9));

        when(repository.findByUsername("maria.s1")).thenReturn(Optional.of(student));

        assertThrows(Exception.class, () -> {
            service.createNewStudent(requestPayload, "Java Developer", EducationalLevel.HIGHER);
        });
    }

    @Test
    void deveLancarExcecaoCampoUsernameInvalido(){
        UserRequestPayload requestPayload = new UserRequestPayload("João Paulo", "jp)", "jpaulo@gmail.com", "pPjo#123", LocalDate.of(2003, 9, 9));
        UserRequestPayload requestPayload2 = new UserRequestPayload("João Pedro", "joao pedro", "joao@outlook.com", "pPjo#123", LocalDate.of(2003, 9, 9));

        assertThrows(Exception.class, () -> {
            service.createNewStudent(requestPayload, "Estudante Angular", EducationalLevel.HIGHER);
        });

        assertThrows(Exception.class, () -> {
            service.createNewStudent(requestPayload2, "Java Developer", EducationalLevel.HIGHER);
        });

    }

    @Test
    void deveLancarExcecaoCampoEmailRepetido(){
        Student student1 = new Student("Maria", "maria.s1", "silva.maria11@gmail.com", "Maria#123", LocalDate.of(1999, 5, 7),"Engenheira de Software", EducationalLevel.MASTERS_DEGREE);

        UserRequestPayload requestPayload = new UserRequestPayload("Maria Eduarda", "maria.s1", "dudam@gmail.com", "Maria#123", LocalDate.of(2004, 8, 9));

        when(repository.findByUsername("maria.s1")).thenReturn(Optional.of(student1));

        assertThrows(Exception.class, () -> {
            service.createNewStudent(requestPayload, "Java Developer", EducationalLevel.HIGHER);
        });
    }

    @Test
    void deveLancarExcecaoCampoEmailInvalido(){
        UserRequestPayload requestPayload = new UserRequestPayload("Maria Eduarda", "maria.s1", "dudaml.com", "Maria#123", LocalDate.of(2004, 8, 9));

        assertThrows(Exception.class, () -> {
            service.createNewStudent(requestPayload, "Java Developer", EducationalLevel.HIGHER);
        });
    }

    @Test
    void deveLancarExcecaoCampoPasswordInvalido(){
        UserRequestPayload requestPayload = new UserRequestPayload("Maria Eduarda", "maria.s1", "dudam@gmail.com", "mariasilva", LocalDate.of(2004, 8, 9));
        UserRequestPayload requestPayload2 = new UserRequestPayload("Maria Eduarda", "mariaduda_s", "dudam23@gmail.com", "hj", LocalDate.of(2005, 8, 9));

        assertThrows(Exception.class, () -> {
            service.createNewStudent(requestPayload, "Java Developer", EducationalLevel.HIGHER);
        });
        assertThrows(Exception.class, () -> {
            service.createNewStudent(requestPayload2, "Java Developer", EducationalLevel.HIGHER);
        });
    }

    @Test
    void deveLancarExcecaoUnderage(){
        UserRequestPayload requestPayload = new UserRequestPayload("Maria Eduarda", "maria.s1", "dudam@gmail.com", "Maria#123", LocalDate.of(2008, 8, 9));

        assertThrows(UnderageException.class, () -> {
            service.createNewStudent(requestPayload, "Java Developer", EducationalLevel.HIGHER);
        });
    }

    @Test
    void deveDeletarUsuario(){
        Student student = new Student("Maria", "maria.s1", "silva.maria11@gmail.com", "Maria#123", LocalDate.of(1999, 5, 7), "Estudante de programação", EducationalLevel.HIGHER);
        student.setId(UUID.randomUUID());

        when(repository.findByUsername("maria.s1")).thenReturn(Optional.of(student));
        when(repository.findById(student.getId())).thenReturn(Optional.of(student));
        doNothing().when(repository).deleteById(student.getId());

        service.deleteStudent(student.getId());

        verify(repository).deleteById(student.getId());
        verify(repository, times(0)).findByUsername("maria.s1");
        verify(repository, times(0)).findById(student.getId());
    }
}
