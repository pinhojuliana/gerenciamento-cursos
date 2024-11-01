package com.juliana.gerenciamento_cursos.repository;

import com.juliana.gerenciamento_cursos.domain.ClassSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClassSectionRepository extends JpaRepository<ClassSection, UUID> {
    List<ClassSection> findByTitle(String title);
    List<ClassSection> findByModules_Id(UUID moduleId);
}
