package com.juliana.gerenciamento_cursos.entity.course;

import com.juliana.gerenciamento_cursos.entity.user.teacher.Teacher;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public CourseResponse createNewCourse(CourseRequestPayload courseRequestPayload){
        Course newCourse = new Course(courseRequestPayload.title(), courseRequestPayload.description());
        courseRepository.save(newCourse);

        return new CourseResponse(newCourse.getId());
    }

    public void addTeacher(Teacher teacher){

    }

    public void addTeachers(List<Teacher> teachers){

    }

    public void deleteCourse(UUID id){
        courseRepository.deleteById(id);
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
