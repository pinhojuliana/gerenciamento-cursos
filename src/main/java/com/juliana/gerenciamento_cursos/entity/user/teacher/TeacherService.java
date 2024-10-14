package com.juliana.gerenciamento_cursos.entity.user.teacher;

import com.juliana.gerenciamento_cursos.entity.user.UserService;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.exceptions.InvalidEmailException;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;

import java.time.LocalDate;

public class TeacherService extends UserService<Teacher> {
    public TeacherService(){
        super();
    }

    public void createNewTeacher(String name, LocalDate birth, String email) throws UnderageException, InvalidEmailException {
        Teacher teacher = new Teacher(name, email, birth);
        users.add(teacher);
    }

    public Teacher verifyExistenceOfTeacher(String email) throws InexistentOptionException {
        return users.stream()
                .filter(t -> t.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new InexistentOptionException("Professor não cadastrado"));
    }
}
