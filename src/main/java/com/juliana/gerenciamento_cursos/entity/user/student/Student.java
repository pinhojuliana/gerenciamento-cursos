package com.juliana.gerenciamento_cursos.entity.user.student;

import com.juliana.gerenciamento_cursos.entity.user.User;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import com.juliana.gerenciamento_cursos.validations.DateValidation;
import com.juliana.gerenciamento_cursos.exceptions.InvalidEmailException;
import com.juliana.gerenciamento_cursos.entity.enrollment.Enrollment;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
@Getter
public class Student extends User {
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> studentEnrollments;
    @Column
    private String description;

    public Student(String name, String email,String password, LocalDate dateOfBirth, String description) throws InvalidEmailException, UnderageException {
        super(name, email, password, dateOfBirth);
        this.studentEnrollments = new ArrayList<>();
        this.description = description;
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
