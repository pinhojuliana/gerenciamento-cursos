package com.juliana.gerenciamento_cursos.Application;

import com.juliana.gerenciamento_cursos.entity.educational_platform.EducationalPlatform;
import com.juliana.gerenciamento_cursos.entity.enrollment.Enrollment;
import com.juliana.gerenciamento_cursos.validations.DateValidation;

import java.util.Scanner;

public class Methods {
    EducationalPlatform educationalPlatform;
    Scanner scanner;

    public void showAllCourses(){
        System.out.println(educationalPlatform.showCourses());
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
        
    }
}
