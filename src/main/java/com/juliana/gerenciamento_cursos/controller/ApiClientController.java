package com.juliana.gerenciamento_cursos.controller;

import com.juliana.gerenciamento_cursos.service.ApiClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiClientController {

    @Autowired
    private ApiClientService apiClientService;

    @GetMapping("/test-api")
    public String testApi() {
        return apiClientService.getApiResponse(); // Chama o método para obter resposta da API
    }
}
