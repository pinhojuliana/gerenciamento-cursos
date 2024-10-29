package com.juliana.gerenciamento_cursos.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* criar metodo para desativar inscrição automaticamente apos a data
* ver como crio os controllers separados
* criar handlers -> ivalidPassowrd pode ser usado para senhas fora do padrao
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
