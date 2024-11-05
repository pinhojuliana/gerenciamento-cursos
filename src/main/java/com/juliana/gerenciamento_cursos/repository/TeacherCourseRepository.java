package com.juliana.gerenciamento_cursos.repository;

import com.juliana.gerenciamento_cursos.domain.teacher_course.TeacherCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, UUID> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO teacher_course (teacher_id, course_id) VALUES (?1, ?2)", nativeQuery = true)
    void addTeacherToCourse(UUID teacherId, UUID courseId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM teacher_course WHERE teacher_id = ?1 AND course_id = ?2", nativeQuery = true)
    void removeTeacherFromCourse(UUID teacherId, UUID courseId);

    @Query(value = "SELECT teacher_id FROM teacher_course WHERE course_id = ?1", nativeQuery = true)
    List<UUID> findTeachersByCourseId(UUID courseId);

    @Query(value = "SELECT course_id FROM teacher_course WHERE teacher_id = ?1", nativeQuery = true)
    List<UUID> findCoursesByTeacherId(UUID teacherId);
}
