package com.juliana.gerenciamento_cursos.Application;

import com.juliana.gerenciamento_cursos.entity.educational_platform.EducationalPlatform;
import com.juliana.gerenciamento_cursos.entity.user.User;
import com.juliana.gerenciamento_cursos.entity.user.UserService;
import com.juliana.gerenciamento_cursos.entity.user.teacher.Teacher;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;

import java.util.Scanner;

public class Main {
    User user;
    UserService userService;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //terminar classe

        while (true){
            System.out.println("=== Bem-vindo ao sistema de gerenciamento de cursos ===");
            System.out.println("Selecione o tipo de acesso: ");
            System.out.println("1 - Administrador");
            System.out.println("2 - Professor");
            System.out.println("3 - Aluno");
            System.out.println("4 - Sair");

            int option = scanner.nextInt();
            scanner.nextLine();

            if(option == 1){
                try {
                    System.out.println("Insira sua senha: ");
                    int password = scanner.nextInt();
                    if (password == 5234) {
                        while (true){
                            System.out.println("Escolha uma opção para continuar: ");
                            System.out.println(" - Sair");
                        }
                    } else {
                        throw new InexistentOptionException("Senha incorreta");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            if(option == 2){
                System.out.println("Antes de continuar, insira seu email");
                String email = scanner.nextLine();

                try {
                    userService.verifyExistenceOfUser(email);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Escolha uma opção:");
                System.out.println("1 - Mostrar todos os cursos");
                System.out.println("2 - Mostrar estudantes");
                System.out.println("3 - Pesquisar estudantes");
                System.out.println("4 - Ver perfil");
                System.out.println("5 - Sair");

            }

            if(option == 3){
                while (true) {
                    System.out.println("Você possui cadastro? (s/n)");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("n")) {
                    }

                    System.out.println("Antes de continuar, insira seu email");
                    String email = scanner.nextLine();

                    try {
                        userService.verifyExistenceOfUser(email);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("Escolha uma opção:");
                    System.out.println(" - Sair");


                }
            }

            if(option == 4){
                break;
            }
        }

        scanner.close();
        System.out.println("Encerrando sistema...");
    }
}
