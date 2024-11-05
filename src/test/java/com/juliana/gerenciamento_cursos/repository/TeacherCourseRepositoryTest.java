package com.juliana.gerenciamento_cursos.repository;

import com.juliana.gerenciamento_cursos.domain.client.Teacher;
import com.juliana.gerenciamento_cursos.domain.course.Course;
import com.juliana.gerenciamento_cursos.domain.teacher_course.TeacherCourse;
import com.juliana.gerenciamento_cursos.service.CourseService;
import com.juliana.gerenciamento_cursos.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ActiveProfiles("test")
class TeacherCourseRepositoryTest {
    @Autowired
    TeacherCourseRepository repository;

    @Test
    void addTeacherToCourse() {
        Teacher teacher = new Teacher("Maria", "mmaria12", "msilva@gmail.com", "P@ssw0rd!", LocalDate.of(1995, 3, 7));
        Course course = new Course("Java", "Crie aplicações com Java e SpringBoot");

        TeacherCourse teacherCourse = new TeacherCourse();
        repository.addTeacherToCourse(teacher.getId(), course.getId());

        List<UUID> result = repository.findCoursesByTeacherId(teacher.getId());
        assertNotNull(result);
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