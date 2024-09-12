package com.juliana.gerenciamento_cursos.entity.educational_platform;

import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.enrollment.Enrollment;
import com.juliana.gerenciamento_cursos.entity.student.Student;
import com.juliana.gerenciamento_cursos.entity.teacher.Teacher;
import com.juliana.gerenciamento_cursos.exceptions.InvalidEmailException;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import com.juliana.gerenciamento_cursos.validations.DateValidation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class EducationalPlatform {
    private List<Course> allCourses = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Enrollment> enrollments = new ArrayList<>();

    public void createNewCourse(String title, String description, Teacher teacher){
        Course course = new Course(title, description, teacher);
        allCourses.add(course);
        teacher.getCoursesTaught().add(course);
    }

    public Course verifyExistenceOfCourse(String title) throws InexistentOptionException {
        for (Course course : allCourses) {
            if (title.equalsIgnoreCase(course.getTitle())) {
                return course;
            }
        }
        throw new InexistentOptionException("Curso não encontrado");
    }

    public String showCourses() {
        StringBuilder listCourses = new StringBuilder();
        for (Course course : allCourses) {
            listCourses.append("-").append(course.getTitle()).append("\n");
        }
        return listCourses.toString();
    }

    public String showStudentsOfCourse(Teacher teacher, Course course){
        StringBuilder studentsFound = new StringBuilder();
        if(course.getTeacher().getEmail().equals(teacher.getEmail())){
            course.showEnrollments();
        }
        return studentsFound.toString();
    }

    public void createNewStudent(String name, LocalDate birth, String email) throws UnderageException, InvalidEmailException {
        Student student = new Student(name, email, birth);
        students.add(student);
    }

    public Student verifyExistenceOfStudent(String email) throws InexistentOptionException {
        for (Student student : students) {
            if (email.equalsIgnoreCase(student.getEmail())) {
                return student;
            }
        }
        throw new InexistentOptionException("Estudante não cadastrado");
    }

    public String showStudents() {
        StringBuilder listStudents = new StringBuilder();
        for (Student student : students) {
            listStudents.append("-").append(student.getName()).append(" - ").append(student.getEmail()).append("\n");
        }
        return listStudents.toString();
    }

    public List<Student> searchStudentName(String name) throws InexistentOptionException{
        List<Student> studentsFound = new ArrayList<>();
        for (Student student : students) {
            if (name.equalsIgnoreCase(student.getName())) {
                studentsFound.add(student);
            }
        }
        if(studentsFound.isEmpty()){
            throw new InexistentOptionException("Estudante não encontrado");
        }
        return studentsFound;
    }

    public List<String> searchStudentNameCourse(String name, Course course) throws InexistentOptionException{
        List<String> studentsFound = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().startsWith(name)) {
                studentsFound.add(student.showStudentPublicProfile());
            }
        }
        if(studentsFound.isEmpty()){
            throw new InexistentOptionException("Estudante não encontrado");
        }
        return studentsFound;
    }


    public void enrollStudentInCourse(Course course, Student student){
        Enrollment enrollment = new Enrollment(course, student);
        enrollments.add(enrollment);
        course.getEnrollments().add(enrollment);
        student.getStudentEnrollments().add(enrollment);
    }

    //ajustar metodo
    public void unsubscribleStudent(Student student, Course course){
        for(Enrollment enrollment : student.getStudentEnrollments()){
            if(enrollment.getCourse().equals(course)){
                student.getStudentEnrollments().remove(enrollment);
                enrollments.remove(enrollment);
                course.getEnrollments().remove(enrollment);
            }
        }
    }

    public Teacher verifyExistenceOfTeacher(String email) throws InexistentOptionException {
        for (Teacher teacher : teachers) {
            if (email.equals(teacher.getEmail())) {
                return teacher;
            }
        }
        throw new InexistentOptionException("Professor não cadastrado");
    }

    public void createNewTeacher(String name, LocalDate birth, String email) throws UnderageException, InvalidEmailException {
        Teacher teacher = new Teacher(name, email, birth);
        teachers.add(teacher);
    }
}

