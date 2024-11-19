package com.juliana.gerenciamento_cursos.controller;

import com.juliana.gerenciamento_cursos.DTOs.auth.AuthStudentDTO;
import com.juliana.gerenciamento_cursos.service.auth.AuthStudent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthStudent authStudent;

    @PostMapping("/student")
    public ResponseEntity<String> createToken(@Valid @RequestBody AuthStudentDTO authStudentDTO) throws AuthenticationException {
        var result = authStudent.execute(authStudentDTO);
        return ResponseEntity.ok(result);
    }

}
