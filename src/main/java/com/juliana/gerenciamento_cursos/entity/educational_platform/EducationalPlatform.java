package com.juliana.gerenciamento_cursos.entity.educational_platform;

import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.entity.course.Course;
import com.juliana.gerenciamento_cursos.entity.enrollment.Enrollment;
import com.juliana.gerenciamento_cursos.entity.user.Student;
import com.juliana.gerenciamento_cursos.entity.user.Teacher;
import com.juliana.gerenciamento_cursos.exceptions.InvalidEmailException;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class EducationalPlatform {
    private List<Course> courses = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Enrollment> enrollments = new ArrayList<>();

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

    public void createNewStudent(String name, LocalDate birth, String email) throws UnderageException, InvalidEmailException {
        Student student = new Student(name, email, birth);
        students.add(student);
    }

    public Student verifyExistenceOfStudent(String email) throws InexistentOptionException {
        return students.stream()
                .filter(e -> e.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new InexistentOptionException("Estudante não cadastrado"));
    }

    public String showStudents() {
        if(students.isEmpty()) {
            return "Lista de estudantes vazia";
        }
        return students.stream()
                .map(Student::showStudentPublicProfile)
                .collect(Collectors.joining("\n"));
    }

    public List<Student> searchStudentName(String name) throws InexistentOptionException{
        List<Student> foundStudents = students.stream()
                .filter(s -> (s.getName().equalsIgnoreCase(name)) || (s.getName().startsWith(name)))
                .toList();

        if (foundStudents.isEmpty()) {
            throw new InexistentOptionException("Nenhum estudante encontrado");
        }

        return foundStudents;
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
        return teachers.stream()
                .filter(t -> t.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new InexistentOptionException("Professor não cadastrado"));

    }

    public void createNewTeacher(String name, LocalDate birth, String email) throws UnderageException, InvalidEmailException {
        Teacher teacher = new Teacher(name, email, birth);
        teachers.add(teacher);
    }
}

