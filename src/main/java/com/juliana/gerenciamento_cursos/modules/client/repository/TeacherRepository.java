package com.juliana.gerenciamento_cursos.modules.client.repository;

import com.juliana.gerenciamento_cursos.modules.client.entity.Teacher;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NonNullApi
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    List<Teacher> findByName(String name);
    Optional<Teacher> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "coursesTaught")
    Optional<Teacher> findById(UUID id);
}
