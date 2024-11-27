package com.juliana.gerenciamento_cursos.modules.unit.repository;

import com.juliana.gerenciamento_cursos.modules.unit.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UnitRepository extends JpaRepository<Unit, UUID> {
    Optional<List<Unit>> findByCourseId(UUID courseId);
}
