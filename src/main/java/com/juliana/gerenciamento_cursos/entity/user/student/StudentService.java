package com.juliana.gerenciamento_cursos.entity.user.student;

import com.juliana.gerenciamento_cursos.entity.enrollment.EnrollmentService;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.exceptions.InvalidEmailException;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService {
    private List<Student> students;
    private EnrollmentService enrollmentService;

    public StudentService(EnrollmentService enrollmentService){
        this.students = new ArrayList<>();
        this.enrollmentService = enrollmentService;
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
                .sorted()
                .collect(Collectors.joining("\n"));
    }

    public List<Student> searchStudentName(String name) throws InexistentOptionException{
        List<Student> foundStudents = students.stream()
                .filter(s -> (s.getName().equalsIgnoreCase(name)) || (s.getName().startsWith(name)))
                .sorted()
                .toList();

        if (foundStudents.isEmpty()) {
            throw new InexistentOptionException("Nenhum estudante encontrado");
        }

        return foundStudents;
    }

    public void unsubscribleStudent(Student student){
        students.removeIf(s -> s.equals(student));
        enrollmentService.getEnrollments().removeIf(e -> e.getStudent().equals(student));
    }

}
