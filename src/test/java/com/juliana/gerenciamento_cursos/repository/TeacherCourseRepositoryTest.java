package com.juliana.gerenciamento_cursos.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TeacherCourseRepositoryTest {
    @Autowired
    TeacherCourseRepository repository;

    @Test
    void addTeacherToCourse() {
     //courseService.addTeacher();
    }

    @Test
    void removeTeacherFromCourse() {
       // courseService.removeTeacher();
    }

    @Test
    void findTeachersByCourseId() {
        //courseService.showTeachersOfCourse();
    }

    @Test
    void findCoursesByTeacherId() {
       // teacherService.showAllCoursesTaught();
    }
}