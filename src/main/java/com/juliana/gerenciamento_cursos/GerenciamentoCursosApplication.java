package com.juliana.gerenciamento_cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* por que nao recebo o id como path variable?
* criar metodo para desativar inscrição automaticamente apos a data
* ver como crio os controllers separados
* criar handlers
* implementar testes
* implementar segurança
* criar cas camadas de acesso manager, teacher e student
* email de confirmação para users*/
@SpringBootApplication
public class GerenciamentoCursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciamentoCursosApplication.class, args);
    }

}
