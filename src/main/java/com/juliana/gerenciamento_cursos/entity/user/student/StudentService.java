package com.juliana.gerenciamento_cursos.entity.user.student;

import com.juliana.gerenciamento_cursos.entity.user.UserRequestPayload;
import com.juliana.gerenciamento_cursos.entity.user.UserResponse;
import com.juliana.gerenciamento_cursos.exceptions.*;
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

    public UserResponse createNewStudent(UserRequestPayload userRequestPayload, String description, EducationalLevel educationalLevel) throws UnderageException {
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

        return new UserResponse(newStudent.getId());
    }

    public void updateStudentDescription(UUID id, String description) throws InexistentOptionException {
        Student student = repository.findById(id).orElseThrow(()-> new InexistentOptionException("Esse usuário não existe"));
        student.setDescription(description);
        repository.save(student);
    }

    public void updateStudentEducationalLevel(UUID id, EducationalLevel educationalLevel) throws InexistentOptionException {
        Student student = repository.findById(id).orElseThrow(()-> new InexistentOptionException("Esse usuário não existe"));
        student.setEducationalLevel(educationalLevel);
        repository.save(student);
    }

    public void updateStudentUsername(UUID id, String username) throws InexistentOptionException {
        validateUniqueUsername(username);
        Student student = repository.findById(id).orElseThrow(()-> new InexistentOptionException("Esse usuário não existe"));
        student.setUsername(username);
        repository.save(student);
    }

    public void updateStudentEmail(UUID id, String email) throws InexistentOptionException {
        validateUniqueEmail(email);
        Student student = repository.findById(id).orElseThrow(()-> new InexistentOptionException("Esse usuário não existe"));
        student.setEmail(email);
        repository.save(student);
    }

    public void updateStudentPassword(UUID id, String oldPassword, String newPassword) throws InexistentOptionException {
        Student student = repository.findById(id).orElseThrow(()-> new InexistentOptionException("Esse usuário não existe"));
        if(!student.getPassword().equals(oldPassword)){
            throw new InvalidPasswordException("A senha atual está incorreta");
        }
        student.setPassword(newPassword);
        repository.save(student);
    }

    public void deleteStudent(UUID id) {
        repository.deleteById(id);
    }

    public List<Student> searchStudentName(String name) throws InexistentOptionException {
        List<Student> students = repository.findByName(name).stream().map(s -> new Student(s.getName(), s.getUsername(), s.getEmail(), s.getDateOfBirth(), s.getDescription(), s.getEducationalLevel()))
                .toList();

        if (students.isEmpty()) {
            throw new InexistentOptionException("Esse nome não foi encontrado");
        }

        return students;
    }

    public Student searchStudent(UUID id) throws InexistentOptionException {
        return repository.findById(id)
                .map(s -> new Student(s.getName(), s.getUsername(), s.getEmail(), s.getDateOfBirth(), s.getDescription(), s.getEducationalLevel()))
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

}
