package com.juliana.gerenciamento_cursos.repository;

import com.juliana.gerenciamento_cursos.domain.client.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByUsername(String username);
    List<Student> findByName(String name);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
