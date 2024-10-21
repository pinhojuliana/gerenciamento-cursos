package com.juliana.gerenciamento_cursos.entity.user.teacher;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
}
