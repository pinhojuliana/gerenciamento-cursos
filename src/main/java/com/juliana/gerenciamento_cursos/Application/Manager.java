package com.juliana.gerenciamento_cursos.Application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Manager {
    private Methods methods;


    public void showOptions(){
        System.out.println("Qual sua senha de acesso à plataforma? ");

    }
}
