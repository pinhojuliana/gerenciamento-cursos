package com.juliana.gerenciamento_cursos.entity.course;

import com.juliana.gerenciamento_cursos.entity.module_section.ModuleSection;
import com.juliana.gerenciamento_cursos.entity.user.teacher.Teacher;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseService {
   /* private List<Course> courses;

    public CourseService(){
        this.courses = new ArrayList<>();
    }

    public ModuleSection verifyExistenceOfModule(String title) throws InexistentOptionException{
        return courses.stream()
                .flatMap(c -> c.getModules().stream())
                .filter(m -> m.getTitle().equalsIgnoreCase(title))
                .findAny()
                .orElseThrow(() -> new InexistentOptionException("Modulo não encontrado"));
    }

    public String showEnrollments(){
        return courses.stream()
                .map(c -> c.getModules().toString())
                .collect(Collectors.joining("\n"));
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
            return this.showEnrollments();
        }
        return "O professor ou o aluno são inválidos";
    }
    */

}
