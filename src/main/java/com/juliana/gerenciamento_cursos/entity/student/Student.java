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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Data
public class Student {
    private UUID id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private List<Enrollment> studentEnrollments;
    private LocalDateTime createdAt;

    public Student(String name, String email, LocalDate dateOfBirth) throws InvalidEmailException, UnderageException {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = EmailValidation.isValidEmail(email);
        if(AgeValidation.validateAge(dateOfBirth)){
        this.dateOfBirth = dateOfBirth;
        }
        this.studentEnrollments = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    public List<Enrollment> showEnrollments(){
        return new ArrayList<>(studentEnrollments);
    }

    @Override
    public String toString(){
        return String.format("Name: %s\nEmail: %s\nDate of birth: %s\nCreated at: %s", name, email, DateValidation.formatDate(dateOfBirth), DateValidation.formatDateTime(createdAt));
    }

}
