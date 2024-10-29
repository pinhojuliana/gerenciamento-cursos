package com.juliana.gerenciamento_cursos.entity.modules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModulesRepository extends JpaRepository<Modules, UUID> {
    List<Modules> findByTitle(String title);
    List<Modules> findByCourseId(UUID courseId);
}
