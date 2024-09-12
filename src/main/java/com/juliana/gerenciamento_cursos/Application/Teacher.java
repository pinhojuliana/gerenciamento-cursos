package com.juliana.gerenciamento_cursos.Application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Scanner;

@Data
@AllArgsConstructor
public class Teacher {
    private Methods methods;

    public void showOptions(){
        System.out.println("Escolha uma opção para contonuar:");
        System.out.println("1 - Mostrar todos os cursos disponíveis");
        System.out.println("2 - Mostrar inscrições em curso em que da aula");
        System.out.println("3 - Buscar estudantes por nome");
        System.out.println("4 - Ver sobre seu perfil");

        int option = methods.getScanner().nextInt();

        switch (option){

        }
    }

}
