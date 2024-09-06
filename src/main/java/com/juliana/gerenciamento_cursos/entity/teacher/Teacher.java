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
    private List<Course> coursesTaught = new ArrayList<>();
    private EducationalPlatform educationalPlatform;

    public Teacher(String name, String email, String dateOfBirth) throws InvalidEmailException, UnderageException {
        this.id = UUID.randomUUID();
        this.name = name;
        if(AgeValidation.validateAge(DateValidation.formatDate(dateOfBirth))){
            this.dateOfBirth = DateValidation.formatDate(dateOfBirth);
        }
        this.email = EmailValidation.isValidEmail(email);
        educationalPlatform.getTeachers().add(this);
    }
}
