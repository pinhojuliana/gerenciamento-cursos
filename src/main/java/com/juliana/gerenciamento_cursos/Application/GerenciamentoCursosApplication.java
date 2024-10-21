package com.juliana.gerenciamento_cursos.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* configurar postgres
* Estudar classes Data, Requeste payload e response
* modificar metodos (ver como eu trabalho com eles com o db)
* criar cas camadas de acesso manager, teacher e student
* ver como crio os controllers separados levando isso em consideração
* criar handlers
* uso flyway?
* implementar testes
* implementar segurança
* email de confirmação*/
@SpringBootApplication
public class GerenciamentoCursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciamentoCursosApplication.class, args);
    }

}
