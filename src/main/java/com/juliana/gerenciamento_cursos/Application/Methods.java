package com.juliana.gerenciamento_cursos.Application;

import com.juliana.gerenciamento_cursos.entity.educational_platform.EducationalPlatform;
import com.juliana.gerenciamento_cursos.validations.DateValidation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Scanner;

@AllArgsConstructor
@Data
public class Methods {

    private EducationalPlatform educationalPlatform;
    private Scanner scanner;

    public void showAllCourses(){
        System.out.println(educationalPlatform.showCourses());
    }

    public void showStudents(){
        System.out.println(educationalPlatform.showStudents());
    }

    public void createNewStudent(Scanner scanner){
        try {
            this.scanner = scanner;
            System.out.println("Para cadastrar um novo estudante primeiro informe o nome completo : ");
            String name = scanner.nextLine();
            System.out.println("Insira um e-mail válido: ");
            String email = scanner.nextLine();
            System.out.println("Insira a data de nascimento: (dd/MM/yyyy)");
            String dateOfBirth = scanner.nextLine();

            educationalPlatform.createNewStudent(name, DateValidation.formatDate(dateOfBirth), email);

            System.out.println("Estudante criado com sucesso. Guarde seu e-mail cadastrado para futuras operações na plataforma.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void enrollStudent(Scanner scanner){
        try {
            this.scanner = scanner;
            System.out.println("Em qual curso deseja se cadastrar?");
            String courseTitle = scanner.nextLine();
            System.out.println("Insira seu e-mail para concluirmos o cadastro:");
            String email = scanner.nextLine();

            educationalPlatform.enrollStudentInCourse(educationalPlatform.verifyExistenceOfCourse(courseTitle), educationalPlatform.verifyExistenceOfStudent(email));

            System.out.println("Cadastro concluído com sucesso");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void seeStudentPersonalProfile(Scanner scanner){
        try {
            this.scanner = scanner;
            System.out.println("Para contiuar insira seu e-mail de acesso à plataforma");
            String email = scanner.nextLine();
            System.out.println(educationalPlatform.verifyExistenceOfStudent(email));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showStudentEnrollments(Scanner scanner){
        try {
            this.scanner = scanner;
            System.out.println("Para contiuar insira seu e-mail de acesso à plataforma");
            String email = scanner.nextLine();

            System.out.println(educationalPlatform.verifyExistenceOfStudent(email).showEnrollments());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void unsubscribeStudent(){
        try{
            System.out.println("Informe seu e-mail:");
            String email = scanner.nextLine();
            System.out.println("Curso que deseja cancelar a inscrição");
            String title = scanner.nextLine();

            educationalPlatform.unsubscribleStudent(educationalPlatform.verifyExistenceOfStudent(email), educationalPlatform.verifyExistenceOfCourse(title));

            System.out.println("Cadastro Cancelado");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void createNewTeacher(Scanner scanner){
        try {
            this.scanner = scanner;
            System.out.println("Para cadastrar um novo estudante primeiro informe o nome completo : ");
            String name = scanner.nextLine();
            System.out.println("Insira um e-mail válido: ");
            String email = scanner.nextLine();
            System.out.println("Insira a data de nascimento: (dd/MM/yyyy)");
            String dateOfBirth = scanner.nextLine();

            educationalPlatform.createNewTeacher(name, DateValidation.formatDate(dateOfBirth), email);

            System.out.println("Professor cadastrado com sucesso. Guarde seu e-mail cadastrado para futuras operações na plataforma.");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showStudentsCourse(Scanner scanner){
        try {
            this.scanner = scanner;
            System.out.println("E-mail do professor:");
            String email = scanner.nextLine();
            System.out.println("Curso:");
            String title = scanner.nextLine();

            System.out.println(educationalPlatform.showStudentsOfCourse(educationalPlatform.verifyExistenceOfTeacher(email), educationalPlatform.verifyExistenceOfCourse(title)));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void seeTeacherPersonalProfile(Scanner scanner){
        try {
            this.scanner = scanner;
            System.out.println("Para contiuar insira seu e-mail de acesso à plataforma");
            String email = scanner.nextLine();
            System.out.println(educationalPlatform.verifyExistenceOfTeacher(email));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void SearchCourseStudentsName(Scanner scanner){
        try {
            this.scanner = scanner;
            System.out.println("Nome do estudante:");
            String name = scanner.nextLine();
            System.out.println("Curso:");
            String title = scanner.nextLine();

            System.out.println(educationalPlatform.searchStudentNameCourse(name, educationalPlatform.verifyExistenceOfCourse(title)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
