package com.juliana.gerenciamento_cursos.entity.course;

import jakarta.persistence.*;
import lombok.Data;

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

    public Course(String title, String description){
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString(){
        return String.format("{Title: %s, Description: %s}", title, description);
    }

}
