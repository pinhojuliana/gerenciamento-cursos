package com.juliana.gerenciamento_cursos.repository;

import com.juliana.gerenciamento_cursos.domain.unit.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UnitRepository extends JpaRepository<Unit, UUID> {
    List<Unit> findByTitle(String title);
    Optional<List<Unit>> findByCourseId(UUID courseId);
}
