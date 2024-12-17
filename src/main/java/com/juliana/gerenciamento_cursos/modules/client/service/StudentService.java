package com.juliana.gerenciamento_cursos.modules.client.service;

import com.juliana.gerenciamento_cursos.modules.client.dto.StudentRequestPayload;
import com.juliana.gerenciamento_cursos.modules.client.dto.ClientResponse;
import com.juliana.gerenciamento_cursos.modules.client.entity.EducationalLevel;
import com.juliana.gerenciamento_cursos.modules.client.entity.Student;
import com.juliana.gerenciamento_cursos.modules.client.dto.StudentDTO;
import com.juliana.gerenciamento_cursos.exceptions.*;
import com.juliana.gerenciamento_cursos.modules.client.repository.StudentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.juliana.gerenciamento_cursos.util.AgeValidation.validateAge;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository repository;

    private final PasswordEncoder passwordEncoder;

    public ClientResponse createNewStudent(@Valid StudentRequestPayload requestPayload) throws InvalidAgeException {
        validateUniqueUsername(requestPayload.username());
        validateUniqueEmail(requestPayload.email());
        validateAge(requestPayload.dateOfBirth());

        var student = Student.builder()
                .name(requestPayload.name())
                .username(requestPayload.username())
                .email(requestPayload.email())
                .password(passwordEncoder.encode(requestPayload.password()))
                .dateOfBirth(requestPayload.dateOfBirth())
                .description(requestPayload.description())
                .educationalLevel(requestPayload.educationalLevel())
                .build();

        repository.save(student);

        return new ClientResponse(student.getId());
    }

    public void updateStudentDescription(UUID id, String description) {
        Student student = validateId(id);
        checkForNoUpdate(student.getDescription(), description);
        student.setDescription(description);
        repository.save(student);
    }

    public void updateStudentEducationalLevel(UUID id, EducationalLevel educationalLevel) {
        Student student = validateId(id);
        checkForNoUpdate(student.getEducationalLevel(), educationalLevel);
        student.setEducationalLevel(educationalLevel);
        repository.save(student);
    }

    public void updateStudentUsername(UUID id, String username) {
        Student student = validateId(id);
        checkForNoUpdate(student.getUsername(), username);
        validateUniqueUsername(username);
        student.setUsername(username);
        repository.save(student);
    }

    public void updateStudentEmail(UUID id, String email) {
        Student student = validateId(id);
        checkForNoUpdate(student.getEmail(), email);
        validateUniqueEmail(email);
        student.setEmail(email);
        repository.save(student);
    }

    public void updateStudentPassword(UUID id, String oldPassword, String newPassword) {
        Student student = validateId(id);
        if(!student.getPassword().equals(oldPassword)){
            throw new InvalidPasswordException("A senha atual está incorreta");
        }
        checkForNoUpdate(oldPassword, newPassword);
        student.setPassword(newPassword);
        repository.save(student);
    }

    public void deleteStudent(UUID id) {
       validateId(id);
       repository.deleteById(id);
    }

    public List<StudentDTO> getAllStudents() throws NoSuchElementException {
        List<StudentDTO> students = repository.findAll().stream().map(this::convertToDTO)
                .toList();

        if (students.isEmpty()) {
            throw new NoSuchElementException("Não há estudantes cadastrados");
        }

        return students;
    }

    public List<StudentDTO> searchStudentName(String name) throws NoSuchElementException{
        List<StudentDTO> students = repository.findByNameContainsIgnoreCase(name).stream().map(this::convertToDTO)
                .toList();

        if (students.isEmpty()) {
            throw new NoSuchElementException("Nenhum estudante com esse nome foi encontrado");
        }

        return students;
    }

    public StudentDTO searchStudentId(UUID id) throws NoSuchElementException {
        return repository.findById(id)
                .map(s -> new StudentDTO(s.getName(), s.getUsername(), s.getEmail(), s.getDateOfBirth(), s.getDescription(), s.getEducationalLevel()))
                .orElseThrow(() -> new NoSuchElementException("Esse id não foi encontrado"));
    }

    private void validateUniqueUsername(String username) {
        if (repository.existsByUsername(username)) {
            throw new UsernameAlreadyInUseException("Esse username já está sendo utilizado por outro usuário");
        }
    }

    private void validateUniqueEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new EmailAlreadyInUseException("Esse e-mail já está sendo utilizado por outro usuário");
        }
    }

    private Student validateId(UUID id){
        return repository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Esse usuário não existe"));
    }

    private <T> void checkForNoUpdate(T oldValue, T newValue) {
        if (oldValue.equals(newValue)) {
            throw new NoUpdateNeededException("Os campos 'novo' e 'atual' não devem ser iguais");
        }
    }

    private StudentDTO convertToDTO(Student student) {
        return new StudentDTO(
                student.getName(),
                student.getUsername(),
                student.getEmail(),
                student.getDateOfBirth(),
                student.getDescription(),
                student.getEducationalLevel()
        );
    }

}
