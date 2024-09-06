package com.juliana.gerenciamento_cursos.entity.educational_platform;

import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.enrollment.Enrollment;
import com.juliana.gerenciamento_cursos.entity.student.Student;
import com.juliana.gerenciamento_cursos.entity.teacher.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class EducationalPlatform {
    private List<Course> allCourses = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Enrollment> enrollments = new ArrayList<>();

    public Teacher verifyExistenceOfTeacher(String email) throws InexistentOptionException {
        for (Teacher teacher : teachers) {
            if (email.equals(teacher.getEmail())) {
                return teacher;
            }
        }
        throw new InexistentOptionException("Professor não cadastrado");
    }

    public Course verifyExistenceOfCourse(String title) throws InexistentOptionException {
        for (Course course : allCourses) {
            if (title.equalsIgnoreCase(course.getTitle())) {
                return course;
            }
        }
        throw new InexistentOptionException("Curso não encontrado");
    }

    public Student verifyExistenceOfStudent(String email) throws InexistentOptionException {
        for (Student student : students) {
            if (email.equalsIgnoreCase(student.getEmail())) {
                return student;
            }
        }
        throw new InexistentOptionException("Estudante não cadastrado");
    }

    public String showAllCourses() {
        StringBuilder listCourses = new StringBuilder();
        for (Course course : allCourses) {
            listCourses.append("-").append(course.getTitle()).append("\n");
        }
        return listCourses.toString();
    }

    public String showStudents() {
        StringBuilder listStudents = new StringBuilder();
        for (Student student : students) {
            listStudents.append("-").append(student.getName()).append(" - ").append(student.getEmail()).append("\n");
        }
        return listStudents.toString();
    }

    public Student searchStudantName(String name) throws InexistentOptionException{
        for (Student student : students) {
            if (name.equalsIgnoreCase(student.getName())) {
                return student;
            }
        }
        throw new InexistentOptionException("Estudante não encontrado");
    }

    //ajeitar esse metodo
    public void unsubscrible(Student student, Course course){
        for(Enrollment enrollment : enrollments){
            if(enrollment.getStudent().equals(student)){
                enrollments.remove(this);
                course.getEnrollments().remove(this);
                student.getStudentEnrollments().remove(this);
            }
        }
    }
}

