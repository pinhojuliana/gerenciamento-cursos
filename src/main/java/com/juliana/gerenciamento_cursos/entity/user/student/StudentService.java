package com.juliana.gerenciamento_cursos.entity.user.student;

import com.juliana.gerenciamento_cursos.entity.user.UserService;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
public class StudentService extends UserService<Student> {

    public void createNewStudent(String name, LocalDate birth, String email) throws UnderageException {

    }

    public void deleteStudent(Student student) {

    }

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
