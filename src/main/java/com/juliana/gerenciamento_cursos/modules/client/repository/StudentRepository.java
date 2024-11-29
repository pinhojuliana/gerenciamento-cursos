package com.juliana.gerenciamento_cursos.modules.client.repository;

import com.juliana.gerenciamento_cursos.modules.client.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findByName(String name); //nao vou mais utilizar
    Optional<Student> findByUsername(String username);
    List<Student> findByNameContainsIgnoreCase(String name);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
