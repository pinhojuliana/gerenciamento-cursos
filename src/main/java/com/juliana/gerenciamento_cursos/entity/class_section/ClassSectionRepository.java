package com.juliana.gerenciamento_cursos.entity.class_section;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClassSectionRepository extends JpaRepository<ClassSection, UUID> {
}
