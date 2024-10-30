package com.juliana.gerenciamento_cursos.entity.modules;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModulesRepository extends JpaRepository<Modules, UUID> {
    List<Modules> findByTitle(String title);
    Optional<Course> findByCourseId(UUID courseId);
}
