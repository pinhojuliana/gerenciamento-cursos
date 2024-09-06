package com.juliana.gerenciamento_cursos.entity.educational_platform;

import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.enrollment.Enrollment;
import com.juliana.gerenciamento_cursos.entity.student.Student;
import com.juliana.gerenciamento_cursos.entity.teacher.Teacher;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EducationalPlatform {
    private List<Course> allCourses;
    private List<Student> students;
    private List<Teacher> teachers;
    private List<Enrollment> enrollments;

    public EducationalPlatform(){
        allCourses = new ArrayList<>();
        students = new ArrayList<>();
        teachers = new ArrayList<>();
        enrollments = new ArrayList<>();
    }

    public Teacher verifyExistenceOfTeacher(String email) throws InexistentOptionException{
        for(Teacher teacher : teachers){
            if(email.equals(teacher.getEmail())){
                return teacher;
            }
        }
        throw new InexistentOptionException("Professor não cadastrado");
    }

    public Course verifyExistenceOfCourse(String title) throws InexistentOptionException{
        for(Course course : allCourses){
            if(title.equalsIgnoreCase(course.getTitle())){
                return course;
            }
        }
        throw new InexistentOptionException("Curso não encontrado");
    }

    public Student verifyExistenceOfStudent(String email) throws InexistentOptionException{
        for(Student student : students){
            if(email.equalsIgnoreCase(student.getEmail())){
                return student;
            }
        }
        throw new InexistentOptionException("Estudante não cadastrado");
    }

}
