package com.juliana.gerenciamento_cursos.repository;

import com.juliana.gerenciamento_cursos.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    Optional<Teacher> findByUsername(String username);
    List<Teacher> findByName(String name);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
