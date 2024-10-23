package com.juliana.gerenciamento_cursos.entity.user.teacher;

import com.juliana.gerenciamento_cursos.entity.user.User;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import com.juliana.gerenciamento_cursos.validations.DateValidation;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher")
@Getter
public class Teacher extends User {
    @ElementCollection
    @CollectionTable(name = "teacher_skill", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "skill")
    private List<String> skills;

    public Teacher(String name, String username, String email, String password, LocalDate dateOfBirth) throws UnderageException {
        super(name, username, email, password, dateOfBirth);
        this.skills = new ArrayList<>();
    }

    @Override
    public String toString(){
        return String.format("{Name: %s, Email: %s, Date of birth: %s}", name, email, DateValidation.formatDate(dateOfBirth));
    }
}
