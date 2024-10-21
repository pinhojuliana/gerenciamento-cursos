package com.juliana.gerenciamento_cursos.entity.user.teacher;

import com.juliana.gerenciamento_cursos.entity.user.User;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import com.juliana.gerenciamento_cursos.validations.DateValidation;
import com.juliana.gerenciamento_cursos.exceptions.InvalidEmailException;
import com.juliana.gerenciamento_cursos.entity.course.Course;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher")
@Getter
public class Teacher extends User {
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "teacher_course",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> coursesTaught;

    public Teacher(String name, String email, String password, LocalDate dateOfBirth) throws InvalidEmailException, UnderageException {
        super(name, email, password, dateOfBirth);
        coursesTaught = new ArrayList<>();
    }

    @Override
    public String toString(){
        return String.format("{Name: %s, Email: %s, Date of birth: %s, Courses taught: %d}", name, email, DateValidation.formatDate(dateOfBirth), coursesTaught);
    }
}
