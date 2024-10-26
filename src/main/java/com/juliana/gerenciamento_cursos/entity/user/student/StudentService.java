package com.juliana.gerenciamento_cursos.entity.user.student;

import com.juliana.gerenciamento_cursos.entity.user.UserRequestPayload;
import com.juliana.gerenciamento_cursos.entity.user.UserResponse;
import com.juliana.gerenciamento_cursos.exceptions.EmailAlreadyInUseException;
import com.juliana.gerenciamento_cursos.exceptions.UsernameAlreadyInUseException;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
public class StudentService {
    //criar metodo privado para verificação

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

    public void updateStudentUsername(UUID id, String username) throws RuntimeException {
        validateUniqueUsername(username);
        Student student = repository.findById(id).orElseThrow(()-> new RuntimeException("Esse usuário não existe"));
        student.setUsername(username);
        repository.save(student);
    }

    public void updateStudentEmail(UUID id, String email) throws RuntimeException {
        validateUniqueEmail(email);
        Student student = repository.findById(id).orElseThrow(()-> new RuntimeException("Esse usuário não existe"));
        student.setEmail(email);
        repository.save(student);
    }

    public void deleteStudent(UUID id) {
        repository.deleteById(id);
    }

    public List<Student> searchStudentName(String name) throws InexistentOptionException {
        List<Student> students = repository.findByName(name);

        if (students.isEmpty()) {
            throw new InexistentOptionException("Esse nome não foi encontrado");
        }

        return students;
    }

    public Student searchStudent(UUID id) throws InexistentOptionException {
        return repository.findById(id)
                .orElseThrow(() ->new InexistentOptionException("Esse id não foi encontrado"));
    }

   public String showStudentPublicProfile(Student student){
        return String.format("{Nome: %s, Escolaridade: %s\nUsername: %s, Email: %s",student.getName(), student.getEducationalLevel(),student.getUsername(), student.getEmail());
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
