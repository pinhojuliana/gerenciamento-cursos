package com.juliana.gerenciamento_cursos.entity.teacher;

import com.juliana.gerenciamento_cursos.entity.educational_platform.EducationalPlatform;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import com.juliana.gerenciamento_cursos.validations.AgeValidation;
import com.juliana.gerenciamento_cursos.validations.DateValidation;
import com.juliana.gerenciamento_cursos.validations.EmailValidation;
import com.juliana.gerenciamento_cursos.exceptions.InvalidEmailException;
import com.juliana.gerenciamento_cursos.entity.course.Course;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Teacher {
    private UUID id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private List<Course> coursesTaught;

    public Teacher(String name, String email, LocalDate dateOfBirth) throws InvalidEmailException, UnderageException {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = EmailValidation.isValidEmail(email);
        if(AgeValidation.validateAge(dateOfBirth)){
            this.dateOfBirth = dateOfBirth;
        }
        coursesTaught = new ArrayList<>();
    }


    @Override
    public String toString(){
        return String.format("Name: %s\nEmail: %s\nDate of birth: %s\nCourses taught: %d", name, email, DateValidation.formatDate(dateOfBirth), coursesTaught);
    }
}
