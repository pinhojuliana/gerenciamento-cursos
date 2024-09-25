package com.juliana.gerenciamento_cursos.entity.user.student;

import com.juliana.gerenciamento_cursos.entity.user.User;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import com.juliana.gerenciamento_cursos.validations.DateValidation;
import com.juliana.gerenciamento_cursos.exceptions.InvalidEmailException;
import com.juliana.gerenciamento_cursos.entity.enrollment.Enrollment;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Student extends User {
    private List<Enrollment> studentEnrollments;

    public Student(String name, String email, LocalDate dateOfBirth) throws InvalidEmailException, UnderageException {
        super(name, email, dateOfBirth);
        this.studentEnrollments = new ArrayList<>();
    }

    public List<Enrollment> showEnrollments(){
        return new ArrayList<>(studentEnrollments);
    }

    @Override
    public String toString(){
        return String.format("Name: %s\nEmail: %s\nDate of birth: %s\nCreated at: %s", name, email, DateValidation.formatDate(dateOfBirth), DateValidation.formatDateTime(createdAt));
    }

    public String showStudentPublicProfile(){
        return String.format("{Name: %s, Date of birth: %s}", name, DateValidation.formatDate(dateOfBirth));
    }



}
