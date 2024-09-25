package com.juliana.gerenciamento_cursos.entity.course;

import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.entity.enrollment.Enrollment;
import com.juliana.gerenciamento_cursos.entity.user.Teacher;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class Course {
    private UUID id;
    private String title;
    private String description;
    private Teacher teacher;
    private List<Enrollment> enrollments;
    private List<ModuleSection> modules;

    public Course(String title, String description, Teacher teacher){
        this.title = title;
        this.description = description;
        this.id = UUID.randomUUID();
        this.teacher = teacher;
        enrollments = new ArrayList<>();
        modules = new ArrayList<>();
    }

    public ModuleSection verifyExistenceOfModule(String title) throws InexistentOptionException{
        return modules.stream()
                .filter(m -> m.getTitle().equalsIgnoreCase(title))
                .findAny()
                .orElseThrow(() -> new InexistentOptionException("Modulo não encontrado"));
    }

    public String showEnrollments(){
        return enrollments.stream()
                .map(Enrollment::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String toString(){
        return String.format("{Title: %s, Description: %s, Teacher: %s}", title, description, teacher.getEmail());
    }

}
