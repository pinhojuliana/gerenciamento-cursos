package com.juliana.gerenciamento_cursos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiClientService {

    @Autowired
    private RestTemplate restTemplate;

    // Exemplo de requisição GET para obter dados da API
    public String getApiResponse() {
        String url = "http://localhost:8080/api/exemplo"; // URL da sua API
        return restTemplate.getForObject(url, String.class);
    }
}
