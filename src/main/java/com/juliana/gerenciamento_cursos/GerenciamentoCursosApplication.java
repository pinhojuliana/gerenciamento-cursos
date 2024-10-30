package com.juliana.gerenciamento_cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* ver como as validações funcionam
* criar metodo para desativar inscrição automaticamente apos a data
* melhorar handlers
* implementar testes
* implementar segurança
* criar cas camadas de acesso manager, teacher e student
* email de confirmação para users -> posso ter que a inscrição é inativa até o estudante confirmar
* */
@SpringBootApplication
public class GerenciamentoCursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciamentoCursosApplication.class, args);
    }

}
