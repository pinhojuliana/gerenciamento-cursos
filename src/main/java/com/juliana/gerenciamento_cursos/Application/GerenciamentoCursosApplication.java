package com.juliana.gerenciamento_cursos.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* ajustar construtores
* quando for criar o metodo e criar cursos adicionar metodo para preencher a tabela
de relacionamento
* estudar classe repository (uso elas no servico com @Autowired)
* nos repositories eu posso criar metodos de busca para implementar no service
* Estudar classes Data, Requeste payload e response
* modificar metodos (ver como eu trabalho com eles com o db)
* ver como crio os controllers separados levando isso em consideração
* criar handlers
* criar cas camadas de acesso manager, teacher e student
* implementar testes
* implementar segurança
* email de confirmação para users*/
@SpringBootApplication
public class GerenciamentoCursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciamentoCursosApplication.class, args);
    }

}
