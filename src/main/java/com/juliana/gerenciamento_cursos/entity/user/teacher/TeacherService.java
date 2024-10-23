package com.juliana.gerenciamento_cursos.entity.user.teacher;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.user.UserService;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
public class TeacherService extends UserService<Teacher> {

    public void createNewTeacher(String name, LocalDate birth, String email) throws UnderageException{

    }

    //recebo um id? um email? um username?
    public void deleteTeacher(Teacher teacher){

    }

    public void verifyExistenceOfTeacher(String email) throws InexistentOptionException {

    }

    public void verifyExistenceOfTeacher(UUID id) throws InexistentOptionException {

    }

    public void findTeacher(String username){

    }

    public void findTeacherCourse(Course course, String nameTeacher){

    }

    public void showAllCoursesTaught(Teacher teacher){

    }

    public void addSkill(String skill) {

    }

    public void removeSkill(String skill) {

    }
}
