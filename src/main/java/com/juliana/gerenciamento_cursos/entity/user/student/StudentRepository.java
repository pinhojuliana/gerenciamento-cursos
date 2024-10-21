package com.juliana.gerenciamento_cursos.entity.user.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}
