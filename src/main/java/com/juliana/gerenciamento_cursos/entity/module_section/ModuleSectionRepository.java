package com.juliana.gerenciamento_cursos.entity.module_section;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ModuleSectionRepository extends JpaRepository<ModuleSection, UUID> {
    Optional<ModuleSection> findByTitle(String title);
}
