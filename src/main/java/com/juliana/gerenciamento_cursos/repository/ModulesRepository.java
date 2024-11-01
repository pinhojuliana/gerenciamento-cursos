package com.juliana.gerenciamento_cursos.repository;

import com.juliana.gerenciamento_cursos.domain.modules.Modules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModulesRepository extends JpaRepository<Modules, UUID> {
    List<Modules> findByTitle(String title);
    Optional<List<Modules>> findByCourseId(UUID courseId);
}
