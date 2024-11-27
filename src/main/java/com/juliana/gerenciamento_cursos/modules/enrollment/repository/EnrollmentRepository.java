package com.juliana.gerenciamento_cursos.modules.enrollment.repository;

import com.juliana.gerenciamento_cursos.modules.enrollment.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);

    Optional<List<Enrollment>> findByStudentId(UUID studentId);
    Optional<List<Enrollment>> findByCourseId(UUID courseId);
    Optional<Enrollment> findByCourseIdAndStudentId(UUID courseId, UUID studentId);
}
