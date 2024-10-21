package com.juliana.gerenciamento_cursos.entity.module_section;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModuleSectionRepository extends JpaRepository<ModuleSection, UUID> {
}
