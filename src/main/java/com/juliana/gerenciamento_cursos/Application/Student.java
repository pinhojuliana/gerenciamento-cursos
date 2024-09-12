package com.juliana.gerenciamento_cursos.Application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;
import java.util.Scanner;

@Data
@AllArgsConstructor
public class Student {
    private Methods methods;

    public void showOptions(){
        System.out.println("Vocẽ é cadastrado(a) na plataforma? (sim/nao)");
        String input = methods.getScanner().nextLine();
        if(input.equalsIgnoreCase("nao")){
            System.out.println("Deseja realizar cadastro? (sim/nao)");
            String input2 = methods.getScanner().nextLine();
            if(input2.equalsIgnoreCase("sim")){
                methods.createNewTeacher(methods.getScanner());
            } else {
                return;
            }
        } else {
            System.out.println("Escolha uma opção para continuar: ");
            System.out.println("1 - Mostrar todos os cursos disponíveis");
            System.out.println("2 - Realizar inscrição em um curso");
            System.out.println("3 - Ver inscrições do seu usuário");
            System.out.println("4 - Ver sobre seu perfil");
            System.out.println("5 - Cancelar inscrição em um curso");

            int option = methods.getScanner().nextInt();

            switch (option){

            }
        }
    }

}
