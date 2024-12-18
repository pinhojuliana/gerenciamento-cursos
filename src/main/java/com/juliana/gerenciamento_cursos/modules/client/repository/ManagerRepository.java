package com.juliana.gerenciamento_cursos.modules.client.repository;

import com.juliana.gerenciamento_cursos.modules.client.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
