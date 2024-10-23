package com.juliana.gerenciamento_cursos.entity.course;

import com.juliana.gerenciamento_cursos.entity.user.teacher.Teacher;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CourseService {

    public void createNewCourse(String title, String description, Teacher teacher){

    }

    public void deleteCourse(Course course){

    }

    public void alterDescription(Course course, String description){

    }

    public void verifyExistenceOfCourse(Course course) throws InexistentOptionException {

    }

    public void showCourses() {

    }

    //o metodo que mostra os cursos sera diferente para o manager

    public void showStudentsOfCourse(Course course) {

    }

    public void showTeachersOfCourse(Course course) {

    }

}
