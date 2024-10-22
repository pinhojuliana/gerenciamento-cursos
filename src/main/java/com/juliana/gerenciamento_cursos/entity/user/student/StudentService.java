package com.juliana.gerenciamento_cursos.entity.user.student;

import com.juliana.gerenciamento_cursos.entity.user.UserService;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.exceptions.InvalidEmailException;
import com.juliana.gerenciamento_cursos.exceptions.UnderageException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService extends UserService<Student> {

    /*public StudentService(){
        super();
    }

    public void createNewStudent(String name, LocalDate birth, String email) throws UnderageException, InvalidEmailException {
        Student student = new Student(name, email, birth);
        createNewUser(student);
    }

    public Student verifyExistenceOfStudent(String email) throws InexistentOptionException {
        return verifyExistenceOfUser(email);
    }

    public String showStudents() {
        if(users.isEmpty()) {
            return "Lista de estudantes vazia";
        }
        return users.stream()
                .map(Student::showStudentPublicProfile)
                .sorted()
                .collect(Collectors.joining("\n"));
    }

    public List<Student> searchStudentName(String name) throws InexistentOptionException{
        List<Student> foundStudents = users.stream()
                .filter(s -> (s.getName().equalsIgnoreCase(name)) || (s.getName().startsWith(name)))
                .sorted()
                .toList();

        if (foundStudents.isEmpty()) {
            throw new InexistentOptionException("Nenhum estudante encontrado");
        }

        return foundStudents;
    }

    public String showStudentPublicProfile(){
        return String.format("{Name: %s, Date of birth: %s}", name, DateValidation.formatDate(dateOfBirth));
    }

    public List<Enrollment> showEnrollments(){
        return new ArrayList<>(studentEnrollments);
    }

    */

}
