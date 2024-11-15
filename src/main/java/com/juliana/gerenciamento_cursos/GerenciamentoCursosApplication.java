package com.juliana.gerenciamento_cursos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = { @Server(url = "/", description = "default Server URL")})
@SpringBootApplication
public class GerenciamentoCursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciamentoCursosApplication.class, args);
    }

}
