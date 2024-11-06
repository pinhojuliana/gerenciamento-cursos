package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.DTOs.StudentDTO;
import com.juliana.gerenciamento_cursos.DTOs.request_payload.StudentRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.response.ClientResponse;
import com.juliana.gerenciamento_cursos.domain.client.EducationalLevel;
import com.juliana.gerenciamento_cursos.domain.client.Student;
import com.juliana.gerenciamento_cursos.exceptions.*;
import com.juliana.gerenciamento_cursos.repository.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//adicionar os 'verify()'
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @InjectMocks
    StudentService service;

    @Mock
    StudentRepository repository;

    @Test
    @DisplayName("deve criar novo estudante")
    void createNewStudentCase1() {
        StudentRequestPayload requestPayload = new StudentRequestPayload(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1995, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );

        Student student = new Student(
                requestPayload.name(),
                requestPayload.username(),
                requestPayload.email(),
                requestPayload.password(),
                requestPayload.dateOfBirth(),
                requestPayload.description(),
                requestPayload.educationalLevel()
        );

        when(repository.save(any(Student.class))).thenReturn(student);

        ClientResponse response = service.createNewStudent(requestPayload);

        verify(repository, times(1)).save(any(Student.class));

        assertEquals(new ClientResponse(student.getId()), response);
    }

    @Test
    @DisplayName("deve lançar exceção de menor idade")
    void createNewStudentCase2() {
        StudentRequestPayload requestPayload = new StudentRequestPayload(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(2008, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );

        Student student = new Student(
                requestPayload.name(),
                requestPayload.username(),
                requestPayload.email(),
                requestPayload.password(),
                requestPayload.dateOfBirth(),
                requestPayload.description(),
                requestPayload.educationalLevel()
        );

        Exception thrown = assertThrows(UnderageException.class, ()-> {
            service.createNewStudent(requestPayload);
        });

        verify(repository, never()).save(any(Student.class));

        assertEquals("Você precisa ter no mínimo 18 anos para se inscrever na plataforma", thrown.getMessage());
    }

    @Test
    @DisplayName("deve lançar exceção de email repetido")
    void createNewStudentCase3() {
        StudentRequestPayload requestPayload1 = new StudentRequestPayload(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );

        Student student1 = new Student(
                requestPayload1.name(),
                requestPayload1.username(),
                requestPayload1.email(),
                requestPayload1.password(),
                requestPayload1.dateOfBirth(),
                requestPayload1.description(),
                requestPayload1.educationalLevel()
        );

        when(repository.save(any(Student.class))).thenReturn(student1);
        service.createNewStudent(requestPayload1);

        StudentRequestPayload requestPayload2 = new StudentRequestPayload(
                "Maria Eduarda", "dudam_", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6), "estudante de java", EducationalLevel.HIGHER
        );

        Student student2 = new Student(
                requestPayload2.name(),
                requestPayload2.username(),
                requestPayload2.email(),
                requestPayload2.password(),
                requestPayload2.dateOfBirth(),
                requestPayload2.description(),
                requestPayload2.educationalLevel()
        );

        when(repository.existsByEmail(student2.getEmail())).thenReturn(true);

        Exception thrown = assertThrows(EmailAlreadyInUseException.class, ()-> {
            service.createNewStudent(requestPayload2);
        });

        assertEquals("Esse e-mail já está sendo utilizado por outro usuário", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("deve lançar exceção de username repetido")
    void createNewStudentCase4() {
        StudentRequestPayload requestPayload1 = new StudentRequestPayload(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );

        Student student1 = new Student(
                requestPayload1.name(),
                requestPayload1.username(),
                requestPayload1.email(),
                requestPayload1.password(),
                requestPayload1.dateOfBirth(),
                requestPayload1.description(),
                requestPayload1.educationalLevel()
        );

        when(repository.save(any(Student.class))).thenReturn(student1);
        service.createNewStudent(requestPayload1);

        StudentRequestPayload requestPayload2 = new StudentRequestPayload(
                "Maria Eduarda", "mmaria12", "mariaedu@outlook.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6), "estudante de java", EducationalLevel.HIGHER
        );

        Student student2 = new Student(
                requestPayload2.name(),
                requestPayload2.username(),
                requestPayload2.email(),
                requestPayload2.password(),
                requestPayload2.dateOfBirth(),
                requestPayload2.description(),
                requestPayload2.educationalLevel()
        );


        when(repository.existsByUsername(student2.getUsername())).thenReturn(true);

        Exception thrown = assertThrows(UsernameAlreadyInUseException.class, ()-> {
            service.createNewStudent(requestPayload2);
        });

        assertEquals("Esse username já está sendo utilizado por outro usuário", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve atualizar descrição com sucesso")
    void updateStudentDescriptionCase1() {
       Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );
       when(repository.findById(any())).thenReturn(Optional.of(student));
       service.updateStudentDescription(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), "Desenvolvedora java, estudante de phyton");
       verify(repository, times(1)).save(student);
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void updateStudentDescriptionCase2() {
        when(repository.findById(any())).thenThrow(new InexistentOptionException("Esse usuário não existe"));

        Exception thrown = assertThrows(InexistentOptionException.class, ()-> {
            service.updateStudentDescription(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), "Desenvolvedora java, estudante de phyton");
        });

        verify(repository, times(1)).findById(any(UUID.class));
        assertEquals("Esse usuário não existe", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção de campos novo e atual iguais")
    void updateStudentDescriptionCase3() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));

        Exception thrown = assertThrows(NoUpdateRequiredException.class, ()-> {
            service.updateStudentDescription(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), "estudante de java");
        });

        assertEquals("Os campos 'novo' e 'atual' não devem ser iguais", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar nivel de escolaridade com sucesso")
    void updateStudentEducationalLevelCase1() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));
        service.updateStudentEducationalLevel(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), EducationalLevel.FUNDAMENTAL);
        verify(repository, times(1)).save(student);
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void updateStudentEducationalLevelCase2() {
        when(repository.findById(any())).thenThrow(new InexistentOptionException("Esse usuário não existe"));

        Exception thrown = assertThrows(InexistentOptionException.class, ()-> {
            service.updateStudentEducationalLevel(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), EducationalLevel.FUNDAMENTAL);
        });

        verify(repository, times(1)).findById(any(UUID.class));
        assertEquals("Esse usuário não existe", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção de campos novo e atual iguais")
    void updateStudentEducationalLevelCase3() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));

        Exception thrown = assertThrows(NoUpdateRequiredException.class, ()-> {
            service.updateStudentEducationalLevel(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), EducationalLevel.HIGHER);
        });

        assertEquals("Os campos 'novo' e 'atual' não devem ser iguais", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar username com sucesso")
    void updateStudentUsernameCase1() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));
        when(repository.existsByUsername(any())).thenReturn(false);

        service.updateStudentUsername(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), "maria.sv");
        verify(repository, times(1)).save(student);
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void updateStudentUsernameCase2() {
        when(repository.findById(any())).thenThrow(new InexistentOptionException("Esse usuário não existe"));

        Exception thrown = assertThrows(InexistentOptionException.class, ()-> {
            service.updateStudentUsername(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), "maria.sv");
        });

        verify(repository, times(1)).findById(any(UUID.class));
        assertEquals("Esse usuário não existe", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção de campos novo e atual iguais")
    void updateStudentUsernameCase3() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));

        Exception thrown = assertThrows(NoUpdateRequiredException.class, ()-> {
            service.updateStudentUsername(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), "mmaria12");
        });

        assertEquals("Os campos 'novo' e 'atual' não devem ser iguais", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção username já utilizado")
    void updateStudentUsernameCase4() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );

        when(repository.findById(any())).thenReturn(Optional.of(student));
        when(repository.existsByUsername(any())).thenReturn(true);

        Exception thrown = assertThrows(UsernameAlreadyInUseException.class, ()-> {
            service.updateStudentUsername(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), "maria.sv2");
        });

        assertEquals("Esse username já está sendo utilizado por outro usuário", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar email com sucesso")
    void updateStudentEmailCase1() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );

        when(repository.findById(any())).thenReturn(Optional.of(student));
        when(repository.existsByUsername(any())).thenReturn(false);
        service.updateStudentEmail(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), "svmaria@gmail.com");
        verify(repository, times(1)).save(student);
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void updateStudentEmailCase2() {
        when(repository.findById(any())).thenThrow(new InexistentOptionException("Esse usuário não existe"));

        Exception thrown = assertThrows(InexistentOptionException.class, ()-> {
            service.updateStudentEmail(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), "maria@outlook.com");
        });

        verify(repository, times(1)).findById(any(UUID.class));
        assertEquals("Esse usuário não existe", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção de campos novo e atual iguais")
    void updateStudentEmailCase3() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));

        Exception thrown = assertThrows(NoUpdateRequiredException.class, ()-> {
            service.updateStudentEmail(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), "msilva@gmail.com");
        });

        assertEquals("Os campos 'novo' e 'atual' não devem ser iguais", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção email já utilizado")
    void updateStudentEmailCase4() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );

        when(repository.findById(any())).thenReturn(Optional.of(student));
        when(repository.existsByUsername(any())).thenReturn(true);

        Exception thrown = assertThrows(UsernameAlreadyInUseException.class, ()-> {
            service.updateStudentEmail(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), "maria.sv@gmail.com");
        });

        assertEquals("Esse email já está sendo utilizado por outro usuário", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar password com sucesso")
    void updateStudentPasswordCase1() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));
        service.updateStudentPassword(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), "P@ssw0rd!", "MyP@ssw0rd1");
        verify(repository, times(1)).save(student);
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void updateStudentPasswordCase2() {
        when(repository.findById(any())).thenThrow(new InexistentOptionException("Esse usuário não existe"));

        Exception thrown = assertThrows(InexistentOptionException.class, ()-> {
            service.updateStudentPassword(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), "P@ssw0rd!", "MyP@ssw0rd1");
        });

        verify(repository, times(1)).findById(any(UUID.class));
        assertEquals("Esse usuário não existe", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção de campos novo e atual iguais")
    void updateStudentPasswordCase3() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));

        Exception thrown = assertThrows(NoUpdateRequiredException.class, ()-> {
            service.updateStudentPassword(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), "P@ssw0rd!", "P@ssw0rd!");
        });

        assertEquals("Os campos 'novo' e 'atual' não devem ser iguais", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção senha atual incorreta")
    void updateStudentPasswordCase4() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));
        Exception thrown = assertThrows(InvalidPasswordException.class, ()-> {
            service.updateStudentPassword(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"), "P@ssw0rd.", "P@ssw0rd!");
        });

        assertEquals("A senha atual está incorreta", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar estudante com sucesso")
    void deleteStudentCase1() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));
        service.deleteStudent(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"));
        verify(repository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Deve lançar exceção id invalido")
    void deleteStudentCase2() {
        when(repository.findById(any())).thenThrow(new InexistentOptionException("Esse usuário não existe"));

        Exception thrown = assertThrows(InexistentOptionException.class, ()-> {
            service.deleteStudent(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"));
        });

        verify(repository, times(1)).findById(any(UUID.class));
        assertEquals("Esse usuário não existe", thrown.getMessage());
        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve retornar estudante da pesquisa do id")
    void searchStudentCase1() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGHER
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));
        StudentDTO studentFound = service.searchStudentId(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"));

        assertEquals(student.getUsername(), studentFound.username());

        verify(repository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Deve retornar exceção de nenhum estudante encontrado")
    void searchStudentCase2() {
        when(repository.findById(any())).thenThrow(new InexistentOptionException("Esse id não foi encontrado"));

        Exception thrown = assertThrows(InexistentOptionException.class, ()-> {
            service.searchStudentId(UUID.fromString("d0f7e9d7-27e5-4c8e-b3fc-96237e9a7f04"));
        });

        verify(repository, times(1)).findById(any());
        assertEquals("Esse id não foi encontrado", thrown.getMessage());
    }



    //terminar

    @Test
    @DisplayName("Deve retornar lista de todos os estudantes")
    void getAllStudentsCase1() {
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void getAllStudentsCase2() {
    }



    //terminar

    @Test
    @DisplayName("Deve retornar lista dos estudantes com o nome pesquisado")
    void searchStudentNameCase1() {
    }

    @Test
    @DisplayName("Deve lançar exceção nenhum nome encontrado")
    void searchStudentNameCase2() {
    }

}