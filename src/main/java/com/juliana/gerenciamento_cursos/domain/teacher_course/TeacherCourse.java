package com.juliana.gerenciamento_cursos.domain.teacher_course;

import com.juliana.gerenciamento_cursos.domain.client.Teacher;
import com.juliana.gerenciamento_cursos.domain.course.Course;
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

    @Column(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(name = "course_id", nullable = false)
    private Course course;
}