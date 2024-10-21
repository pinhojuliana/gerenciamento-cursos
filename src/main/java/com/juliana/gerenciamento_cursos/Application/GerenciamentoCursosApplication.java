package com.juliana.gerenciamento_cursos.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* configurar db -> verificar
* criar tabela de usuarios com todos os usuarios e seus respectivos ids
* criar tabela de alunos
* criar tabela professores
* pesquisar como fica sendo herança
* tabela curso
* tabela modulo com chave estrangeira curso
* tabela aula com chave estrangeira modulo
* tabela inscriçoes
* pesquisar como ficam os atributos agora com as tabelas
* modificar metodos (ver como eu trabalho com eles com o db)
* criar controllers
* criar cas camadas de acesso manager, teacher e student
* ver como crio os controllers separados levando isso em consideração
* criar handlers
* uso flyway?
* implementar testes
* implementar segurança*/
@SpringBootApplication
public class GerenciamentoCursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciamentoCursosApplication.class, args);
    }

}
