package com.juliana.gerenciamento_cursos.domain.teacher_course;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "teacher_course")
@Getter
@Setter
public class TeacherCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "teacher_id")
    private UUID teacherId;

    @Column(name = "course_id")
    private UUID courseId;
}