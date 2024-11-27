package com.juliana.gerenciamento_cursos.modules.course.repository;

import com.juliana.gerenciamento_cursos.modules.course.entity.Course;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

@NonNullApi
public interface CourseRepository extends JpaRepository<Course, UUID> {
    Optional<Course> findByTitle(String title);
    boolean existsByTitle(String title);

    @EntityGraph(attributePaths = "teachers")
    Optional<Course> findById(UUID id);
}
