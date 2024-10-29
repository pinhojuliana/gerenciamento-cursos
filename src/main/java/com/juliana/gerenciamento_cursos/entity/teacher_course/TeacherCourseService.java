package com.juliana.gerenciamento_cursos.application.entity.teacher_course;

import com.juliana.gerenciamento_cursos.application.entity.course.Course;
import com.juliana.gerenciamento_cursos.application.entity.course.CourseRepository;
import com.juliana.gerenciamento_cursos.application.entity.teacher.Teacher;
import com.juliana.gerenciamento_cursos.application.entity.teacher.TeacherRepository;
import com.juliana.gerenciamento_cursos.application.exceptions.InexistentOptionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class TeacherCourseService {
    @Autowired
    TeacherCourseRepository teacherCourseRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TeacherRepository teacherRepository;

    public List<String> showAllCoursesTaught(UUID teacherId) throws InexistentOptionException {
        List<UUID> idCursos = teacherCourseRepository.findCoursesByTeacherId(teacherId);

        if(idCursos.isEmpty()){
            throw new InexistentOptionException("Nada encontrado");
        }

        return idCursos.stream()
                .map(id -> {
                    try {
                        return courseRepository.findById(id).orElseThrow(() -> new InexistentOptionException("Nada encontrado"));
                    } catch (InexistentOptionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(Course::getTitle)
                .toList();
    }

    public void addTeacher(UUID teacherId, UUID courseId){
        teacherCourseRepository.addTeacherToCourse(teacherId, courseId);
    }

    public void removeTeacher(UUID teacherId, UUID courseId){
        teacherCourseRepository.removeTeacherFromCourse(teacherId, courseId);
    }

    public List<String> showTeachersOfCourse(UUID courseId) throws InexistentOptionException {
        List<UUID> idTeachers = teacherCourseRepository.findTeachersByCourseId(courseId);

        if(idTeachers.isEmpty()){
            throw new InexistentOptionException("Nada encontrado");
        }

        return idTeachers.stream()
                .map(id -> {
                    try {
                        return teacherRepository.findById(id).orElseThrow(() -> new InexistentOptionException("Nada encontrado"));
                    } catch (InexistentOptionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(Teacher::getName)
                .toList();
    }
}
