package com.juliana.gerenciamento_cursos.repository;

import com.juliana.gerenciamento_cursos.domain.enrollment.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    Optional<List<Enrollment>> findByStudentId(UUID studentId);
    Optional<List<Enrollment>> findByCourseId(UUID courseId);
    Optional<Enrollment> findByStudentIdAndCourseId(UUID studentId, UUID courseId);
    boolean existsByStudentId(UUID studentId);
    boolean existsByCourseId(UUID courseId);
    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);
}
