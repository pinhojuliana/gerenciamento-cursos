package com.juliana.gerenciamento_cursos.modules.lesson.repository;

import com.juliana.gerenciamento_cursos.modules.lesson.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    Optional<List<Lesson>> findByUnit_id(UUID unitId);

    Optional<List<Lesson>> findByTitleContainsIgnoreCase(String title);
}
