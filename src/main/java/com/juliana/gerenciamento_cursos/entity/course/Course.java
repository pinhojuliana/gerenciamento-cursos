package com.juliana.gerenciamento_cursos.entity.course;

import com.juliana.gerenciamento_cursos.entity.module_section.ModuleSection;
import com.juliana.gerenciamento_cursos.entity.enrollment.Enrollment;
import com.juliana.gerenciamento_cursos.entity.user.teacher.Teacher;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Override
    public String toString(){
        return String.format("{Title: %s, Description: %s, Teacher: %s}", title, description, teacher.getEmail());
    }

}
