package com.juliana.gerenciamento_cursos.repository;

import com.juliana.gerenciamento_cursos.domain.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    List<Lesson> findByTitle(String title);
    List<Lesson> findByModules_Id(UUID moduleId);
}
