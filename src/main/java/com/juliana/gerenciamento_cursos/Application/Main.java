package com.juliana.gerenciamento_cursos.Application;

import com.juliana.gerenciamento_cursos.entity.educational_platform.EducationalPlatform;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EducationalPlatform educationalPlatform = new EducationalPlatform();

        Methods methods = new Methods(educationalPlatform, scanner);

        System.out.println("Bem-vindo ao sistema de Cursos");
        System.out.println("Primeiro informe sua identidade ou encerre o programa escolhendo a opção 'sair':");
        System.out.println("1-Administrador\n2-Professor\n3-Aluno\n4-sair");
        int option = scanner.nextInt();

                switch (option){
                    case 1:
                        Manager manager = new Manager(methods);
                        break;
                    case 2:
                        Teacher teacher = new Teacher(methods);
                        break;
                    case 3:
                        Student student = new Student(methods);
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Opção inválida");
                }


    }
}
