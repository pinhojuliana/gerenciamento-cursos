package com.juliana.gerenciamento_cursos.modules.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
