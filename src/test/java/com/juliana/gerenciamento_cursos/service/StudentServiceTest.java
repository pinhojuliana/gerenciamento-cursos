package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.modules.client.dto.StudentDTO;
import com.juliana.gerenciamento_cursos.modules.client.dto.StudentRequestPayload;
import com.juliana.gerenciamento_cursos.modules.client.dto.ClientResponse;
import com.juliana.gerenciamento_cursos.modules.client.entity.EducationalLevel;
import com.juliana.gerenciamento_cursos.modules.client.entity.Student;
import com.juliana.gerenciamento_cursos.exceptions.*;
import com.juliana.gerenciamento_cursos.modules.client.service.StudentService;
import com.juliana.gerenciamento_cursos.modules.client.repository.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
                LocalDate.of(1995, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
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
                LocalDate.of(2008, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        Exception thrown = assertThrows(InvalidAgeException.class, ()-> {
            service.createNewStudent(requestPayload);
        });

        verify(repository, never()).save(any(Student.class));

        assertEquals("Você precisa ter no mínimo 18 anos para se inscrever na plataforma", thrown.getMessage());
    }

    @Test
    @DisplayName("deve lançar exceção de email repetido")
    void createNewStudentCase3() {

        StudentRequestPayload requestPayload = new StudentRequestPayload(
                "Maria Eduarda", "dudam_", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        when(repository.existsByEmail(requestPayload.email())).thenReturn(true);

        Exception thrown = assertThrows(EmailAlreadyInUseException.class, ()-> {
            service.createNewStudent(requestPayload);
        });

        assertEquals("Esse e-mail já está sendo utilizado por outro usuário", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("deve lançar exceção de username repetido")
    void createNewStudentCase4() {
        StudentRequestPayload requestPayload = new StudentRequestPayload(
                "Maria Eduarda", "mmaria12", "mariaedu@outlook.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        when(repository.existsByUsername(requestPayload.username())).thenReturn(true);

        Exception thrown = assertThrows(UsernameAlreadyInUseException.class, ()-> {
            service.createNewStudent(requestPayload);
        });

        assertEquals("Esse username já está sendo utilizado por outro usuário", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve atualizar descrição com sucesso")
    void updateStudentDescriptionCase1() {
       Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );
       when(repository.findById(any())).thenReturn(Optional.of(student));
       service.updateStudentDescription(any(), "Desenvolvedora java, estudante de phyton");
       verify(repository, times(1)).save(student);
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void updateStudentDescriptionCase2() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(NoSuchElementException.class, ()-> {
       UUID id = UUID.randomUUID();
            service.updateStudentDescription(id, "Desenvolvedora java, estudante de phyton");
        });

        verify(repository, times(1)).findById(any());
        assertEquals("Esse usuário não existe", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve lançar exceção de campos novo e atual iguais")
    void updateStudentDescriptionCase3() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));

        Exception thrown = assertThrows(NoUpdateNeededException.class, ()-> {
            service.updateStudentDescription(any(), "estudante de java");
        });

        assertEquals("Os campos 'novo' e 'atual' não devem ser iguais", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve atualizar nivel de escolaridade com sucesso")
    void updateStudentEducationalLevelCase1() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));
        service.updateStudentEducationalLevel(any(), EducationalLevel.ELEMENTARY);
        verify(repository, times(1)).save(student);
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void updateStudentEducationalLevelCase2() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(NoSuchElementException.class, ()-> {
            service.updateStudentEducationalLevel(id, EducationalLevel.ELEMENTARY);
        });

        verify(repository, times(1)).findById(any());
        assertEquals("Esse usuário não existe", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve lançar exceção de campos novo e atual iguais")
    void updateStudentEducationalLevelCase3() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        UUID id = UUID.randomUUID();
        when(repository.findById(any())).thenReturn(Optional.of(student));

        Exception thrown = assertThrows(NoUpdateNeededException.class, ()-> {
            service.updateStudentEducationalLevel(id, EducationalLevel.HIGH_SCHOOL);
        });

        assertEquals("Os campos 'novo' e 'atual' não devem ser iguais", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve atualizar username com sucesso")
    void updateStudentUsernameCase1() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));
        when(repository.existsByUsername(any())).thenReturn(false);

        service.updateStudentUsername(any(), "maria.sv");
        verify(repository, times(1)).save(student);
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void updateStudentUsernameCase2() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(NoSuchElementException.class, ()-> {
            service.updateStudentUsername(id, "maria.sv");
        });

        verify(repository, times(1)).findById(any());
        assertEquals("Esse usuário não existe", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve lançar exceção de campos novo e atual iguais")
    void updateStudentUsernameCase3() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));

        Exception thrown = assertThrows(NoUpdateNeededException.class, ()-> {
            service.updateStudentUsername(any(), "mmaria12");
        });

        assertEquals("Os campos 'novo' e 'atual' não devem ser iguais", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve lançar exceção username já utilizado")
    void updateStudentUsernameCase4() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        when(repository.findById(any())).thenReturn(Optional.of(student));
        when(repository.existsByUsername(any())).thenReturn(true);

        Exception thrown = assertThrows(UsernameAlreadyInUseException.class, ()-> {
            service.updateStudentUsername(any(), "maria.sv2");
        });

        assertEquals("Esse username já está sendo utilizado por outro usuário", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve atualizar email com sucesso")
    void updateStudentEmailCase1() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));
        when(repository.existsByEmail(any())).thenReturn(false);
        service.updateStudentEmail(any(), "svmaria@gmail.com");
        verify(repository, times(1)).save(student);
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void updateStudentEmailCase2() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(NoSuchElementException.class, ()-> {
            service.updateStudentEmail(id, "maria@outlook.com");
        });

        verify(repository, times(1)).findById(any());
        assertEquals("Esse usuário não existe", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve lançar exceção de campos novo e atual iguais")
    void updateStudentEmailCase3() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));

        Exception thrown = assertThrows(NoUpdateNeededException.class, ()-> {
            service.updateStudentEmail(any(), "msilva@gmail.com");
        });

        assertEquals("Os campos 'novo' e 'atual' não devem ser iguais", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve lançar exceção email já utilizado")
    void updateStudentEmailCase4() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        when(repository.findById(any())).thenReturn(Optional.of(student));
        when(repository.existsByEmail(any())).thenReturn(true);

        Exception thrown = assertThrows(EmailAlreadyInUseException.class, ()-> {
            service.updateStudentEmail(any(), "maria.sv@gmail.com");
        });

        assertEquals("Esse e-mail já está sendo utilizado por outro usuário", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve atualizar password com sucesso")
    void updateStudentPasswordCase1() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));
        service.updateStudentPassword(any(), "P@ssw0rd!", "MyP@ssw0rd1");
        verify(repository, times(1)).save(student);
    }

    @Test
    @DisplayName("Deve lançar exceção id inválido")
    void updateStudentPasswordCase2() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(NoSuchElementException.class, ()-> {
            service.updateStudentPassword(id, "P@ssw0rd!", "MyP@ssw0rd1");
        });

        verify(repository, times(1)).findById(any());
        assertEquals("Esse usuário não existe", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve lançar exceção de campos novo e atual iguais")
    void updateStudentPasswordCase3() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));

        Exception thrown = assertThrows(NoUpdateNeededException.class, ()-> {
            service.updateStudentPassword(any(), "P@ssw0rd!", "P@ssw0rd!");
        });

        assertEquals("Os campos 'novo' e 'atual' não devem ser iguais", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve lançar exceção senha atual incorreta")
    void updateStudentPasswordCase4() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));
        Exception thrown = assertThrows(InvalidPasswordException.class, ()-> {
            service.updateStudentPassword(any(), "P@ssw0rd.", "P@ssw0rd!");
        });

        assertEquals("A senha atual está incorreta", thrown.getMessage());
        verify(repository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Deve deletar estudante com sucesso")
    void deleteStudentCase1() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));
        service.deleteStudent(any());
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("Deve lançar exceção id invalido")
    void deleteStudentCase2() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(NoSuchElementException.class, ()-> {
            service.deleteStudent(id);
        });

        verify(repository, times(1)).findById(any());
        assertEquals("Esse usuário não existe", thrown.getMessage());
        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve retornar estudante da pesquisa do id")
    void searchStudentCase1() {
        Student student = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );
        when(repository.findById(any())).thenReturn(Optional.of(student));
        StudentDTO studentFound = service.searchStudentId(any());

        assertEquals(student.getUsername(), studentFound.username());

        verify(repository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Deve retornar exceção de nenhum estudante encontrado")
    void searchStudentCase2() {
        when(repository.findById(any())).thenThrow(new NoSuchElementException("Esse id não foi encontrado"));

        Exception thrown = assertThrows(NoSuchElementException.class, ()-> {
            service.searchStudentId(any());
        });

        verify(repository, times(1)).findById(any());
        assertEquals("Esse id não foi encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de todos os estudantes")
    void getAllStudentsCase1() {
        Student student = new Student(
                "Maria Eduarda", "dudam_", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        Student student2 = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student2);

        List<StudentDTO> studentsConverted = students.stream().map(s -> new StudentDTO(
                s.getName(),
                s.getUsername(),
                s.getEmail(),
                s.getDateOfBirth(),
                s.getDescription(),
                s.getEducationalLevel()))
                .toList();

        when(repository.findAll()).thenReturn(students);
        List<StudentDTO> result = service.getAllStudents();

        assertEquals(studentsConverted, result);
    }

    @Test
    @DisplayName("Deve lançar exceção lista vazia")
    void getAllStudentsCase2() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        Exception thrown = assertThrows(EmptyListException.class, () ->
        {
            service.getAllStudents();
        });

        assertEquals("Não há estudantes cadastrados", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista dos estudantes com o nome pesquisado")
    void searchStudentNameCase1() {
        Student student = new Student(
                "Maria", "silva.mr", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(2000, 5, 6), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        Student student2 = new Student(
                "Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!",
                LocalDate.of(1997, 3, 7), "estudante de java", EducationalLevel.HIGH_SCHOOL
        );

        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student2);

        List<StudentDTO> studentsConverted = students.stream().map(s -> new StudentDTO(
                        s.getName(),
                        s.getUsername(),
                        s.getEmail(),
                        s.getDateOfBirth(),
                        s.getDescription(),
                        s.getEducationalLevel()))
                .toList();

        when(repository.findByName(any())).thenReturn(students);
        List<StudentDTO> result = service.searchStudentName(any());

        assertEquals(studentsConverted, result);
    }

    @Test
    @DisplayName("Deve lançar exceção nenhum nome encontrado")
    void searchStudentNameCase2() {
        when(repository.findByName(any())).thenReturn(new ArrayList<>());

        Exception thrown = assertThrows(EmptyListException.class, () ->
        {
            service.searchStudentName(any());
        });

        assertEquals("Nenhum estudante com esse nome foi encontrado", thrown.getMessage());

    }

}