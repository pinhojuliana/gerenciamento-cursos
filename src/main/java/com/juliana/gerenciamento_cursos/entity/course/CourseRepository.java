package com.juliana.gerenciamento_cursos.application.entity.course;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    Optional<Course> findByTitle(String title);
    boolean existsByTitle(String title);
}
