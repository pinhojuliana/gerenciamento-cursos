package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.DTOs.ClientRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.ClientResponse;
import com.juliana.gerenciamento_cursos.domain.client.EducationalLevel;
import com.juliana.gerenciamento_cursos.domain.client.Student;
import com.juliana.gerenciamento_cursos.DTOs.StudentDTO;
import com.juliana.gerenciamento_cursos.exceptions.*;
import com.juliana.gerenciamento_cursos.repository.StudentRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
public class StudentService {
    @Autowired
    StudentRepository repository;

    public ClientResponse createNewStudent(ClientRequestPayload userRequestPayload, String description, EducationalLevel educationalLevel) throws UnderageException {
        validateUniqueUsername(userRequestPayload.username());
        validateUniqueEmail(userRequestPayload.email());

        Student newStudent = new Student(userRequestPayload.name(),
                userRequestPayload.username(),
                userRequestPayload.email(),
                userRequestPayload.password(),
                userRequestPayload.dateOfBirth(),
                description,
                educationalLevel);

        repository.save(newStudent);

        return new ClientResponse(newStudent.getId());
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
        checkForNoUpdate(oldPassword, newPassword);
        if(!student.getPassword().equals(oldPassword)){
            throw new InvalidPasswordException("A senha atual está incorreta");
        }
        student.setPassword(newPassword);
        repository.save(student);
    }

    public void deleteStudent(UUID id) {
       validateId(id);
       repository.deleteById(id);
    }

    public List<StudentDTO> getAllStudents() throws EmptyListException {
        List<StudentDTO> students = repository.findAll().stream().map(this::convertToDTO)
                .toList();

        if (students.isEmpty()) {
            throw new EmptyListException("Não há estudantes cadastrados");
        }

        return students;
    }

    public List<StudentDTO> searchStudentName(String name) throws EmptyListException {
        List<StudentDTO> students = repository.findByName(name).stream().map(this::convertToDTO)
                .toList();

        if (students.isEmpty()) {
            throw new EmptyListException("Esse nome não foi encontrado");
        }

        return students;
    }

    public StudentDTO searchStudent(UUID id) throws InexistentOptionException {
        return repository.findById(id)
                .map(s -> new StudentDTO(s.getName(), s.getUsername(), s.getEmail(), s.getDateOfBirth(), s.getDescription(), s.getEducationalLevel()))
                .orElseThrow(() -> new InexistentOptionException("Esse id não foi encontrado"));
    }

    private void validateUniqueUsername(String username) {
        if (repository.existsByUsername(username)) {
            throw new UsernameAlreadyInUseException("Esse username está sendo utilizado por outro usuário");
        }
    }

    private void validateUniqueEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new EmailAlreadyInUseException("Esse e-mail está sendo utilizado por outro usuário");
        }
    }

    private Student validateId(UUID id){
        return repository.findById(id)
                .orElseThrow(()-> new InexistentOptionException("Esse usuário não existe"));
    }

    private <T> void checkForNoUpdate(T oldValue, T newValue) {
        if (oldValue.equals(newValue)) {
            throw new NoUpdateRequiredException("Os campos 'novo' e 'atual' não devem ser iguais");
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
