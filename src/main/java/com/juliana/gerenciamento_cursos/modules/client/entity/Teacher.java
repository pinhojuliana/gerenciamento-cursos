package com.juliana.gerenciamento_cursos.modules.client.entity;

import com.juliana.gerenciamento_cursos.modules.course.entity.Course;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "teacher")
@NoArgsConstructor
@Data
@SuperBuilder
public class Teacher extends Client {
    @ElementCollection
    @CollectionTable(name = "teacher_skill", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "skill")
    private Set<String> skills;

    @ManyToMany(mappedBy = "teachers")
    private Set<Course> coursesTaught;
}
