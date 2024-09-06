package com.juliana.gerenciamento_cursos.entity.student;

import com.juliana.gerenciamento_cursos.entity.educational_platform.EducationalPlatform;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import com.juliana.gerenciamento_cursos.validations.AgeValidation;
import com.juliana.gerenciamento_cursos.validations.DateValidation;
import com.juliana.gerenciamento_cursos.validations.EmailValidation;
import com.juliana.gerenciamento_cursos.exceptions.InvalidEmailException;
import com.juliana.gerenciamento_cursos.entity.enrollment.Enrollment;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Student {
    private UUID id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private List<Enrollment> studentEnrollments;
    private EducationalPlatform educationalPlatform;

    public Student(String name, String email, String dateOfBirth, EducationalPlatform educationalPlatform) throws InvalidEmailException, UnderageException {
        this.id = UUID.randomUUID();
        this.name = name;
        if(AgeValidation.validateAge(DateValidation.formatDate(dateOfBirth))){
        this.dateOfBirth = DateValidation.formatDate(dateOfBirth);
        }
        this.studentEnrollments = new ArrayList<>();
        this.email = EmailValidation.isValidEmail(email);
        educationalPlatform.getStudents().add(this);
    }

}
