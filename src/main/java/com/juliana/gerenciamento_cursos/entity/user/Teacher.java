package com.juliana.gerenciamento_cursos.entity.user;

import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import com.juliana.gerenciamento_cursos.validations.DateValidation;
import com.juliana.gerenciamento_cursos.exceptions.InvalidEmailException;
import com.juliana.gerenciamento_cursos.entity.course.Course;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Teacher extends User {
    private List<Course> coursesTaught;

    public Teacher(String name, String email, LocalDate dateOfBirth) throws InvalidEmailException, UnderageException {
        super(name, email, dateOfBirth);
        coursesTaught = new ArrayList<>();
    }

    @Override
    public String toString(){
        return String.format("Name: %s\nEmail: %s\nDate of birth: %s\nCourses taught: %d", name, email, DateValidation.formatDate(dateOfBirth), coursesTaught);
    }
}
