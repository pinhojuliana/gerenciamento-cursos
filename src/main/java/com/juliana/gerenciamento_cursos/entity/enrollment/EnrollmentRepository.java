package com.juliana.gerenciamento_cursos.entity.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    Optional<List<Enrollment>> findByStudentId(UUID studentId);
    Optional<List<Enrollment>> findByCourseId(UUID courseId);

    Optional<Enrollment> findFirstByStudentId(UUID studentId);
}
