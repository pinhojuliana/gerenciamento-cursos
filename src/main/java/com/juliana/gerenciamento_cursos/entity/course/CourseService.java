package com.juliana.gerenciamento_cursos.entity.course;

import com.juliana.gerenciamento_cursos.entity.user.teacher.Teacher;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseService {
    private List<Course> courses;

    public CourseService(){
        this.courses = new ArrayList<>();
    }

    public void createNewCourse(String title, String description, Teacher teacher){
        Course course = new Course(title, description, teacher);
        courses.add(course);
        teacher.getCoursesTaught().add(course);
    }

    public Course verifyExistenceOfCourse(String title) throws InexistentOptionException {
        return courses.stream()
                .filter(c -> c.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElseThrow(() -> new InexistentOptionException("Curso não encontrado"));
    }

    public String showCourses() {
        return courses.stream()
                .map(c -> c.getTitle())
                .collect(Collectors.joining("\n"));
    }

    public String showStudentsOfCourse(Teacher teacher, Course course) {
        if(course.getTeacher().getEmail().equals(teacher.getEmail())){
            return course.showEnrollments();
        }
        return "O professor ou o aluno são inválidos";
    }
}
