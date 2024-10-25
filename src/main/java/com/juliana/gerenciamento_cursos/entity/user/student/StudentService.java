package com.juliana.gerenciamento_cursos.entity.user.student;

import com.juliana.gerenciamento_cursos.entity.user.UserRequestPayload;
import com.juliana.gerenciamento_cursos.entity.user.UserResponse;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
public class StudentService {
    //criar metodo privado para verificação

    @Autowired
    StudentRepository studentRepository;

    public UserResponse createNewStudent(UserRequestPayload userRequestPayload, String description, EducationalLevel educationalLevel) throws UnderageException {
        Student newStudent = new Student(userRequestPayload.name(),
                userRequestPayload.username(),
                userRequestPayload.email(),
                userRequestPayload.password(),
                userRequestPayload.dateOfBirth(),
                description,
                educationalLevel);

        studentRepository.save(newStudent);

        return new UserResponse(newStudent.getId());
    }

    public void deleteStudent(UUID id) {
        studentRepository.deleteById(id);
    }

    //ja tem um metodo
    public void verifyExistenceOfStudent(String email) throws InexistentOptionException {

    }

    public void verifyExistenceOfStudent(UUID id) throws InexistentOptionException {

    }

    public void searchStudentName(String name) throws InexistentOptionException {

    }

    public void searchStudentName(UUID id) throws InexistentOptionException {

    }

    //public String showStudentPublicProfile(){}

}
