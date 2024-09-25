package com.juliana.gerenciamento_cursos.entity.user.teacher;

import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.exceptions.InvalidEmailException;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeacherService {
    private List<Teacher> teachers;

    public TeacherService(){
        this.teachers = new ArrayList<>();
    }

    public Teacher verifyExistenceOfTeacher(String email) throws InexistentOptionException {
        return teachers.stream()
                .filter(t -> t.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new InexistentOptionException("Professor não cadastrado"));
    }

    public void createNewTeacher(String name, LocalDate birth, String email) throws UnderageException, InvalidEmailException {
        Teacher teacher = new Teacher(name, email, birth);
        teachers.add(teacher);
    }
}
