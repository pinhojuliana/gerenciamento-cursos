package com.juliana.gerenciamento_cursos.entity.course;

import com.juliana.gerenciamento_cursos.entity.module_section.ModuleSection;
import com.juliana.gerenciamento_cursos.entity.enrollment.Enrollment;
import com.juliana.gerenciamento_cursos.entity.user.teacher.Teacher;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "course")
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "coursesTaught")
    private List<Teacher> teachers;

    public Course(String title, String description, Teacher teacher){
        this.title = title;
        this.description = description;
        this.teachers = new ArrayList<>();
    }

    @Override
    public String toString(){
        return String.format("{Title: %s, Description: %s}", title, description);
    }

}
