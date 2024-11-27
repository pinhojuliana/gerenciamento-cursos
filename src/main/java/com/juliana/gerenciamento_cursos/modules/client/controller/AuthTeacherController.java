package com.juliana.gerenciamento_cursos.modules.client.controller;

import com.juliana.gerenciamento_cursos.modules.client.dto.AuthTeacherDTO;
import com.juliana.gerenciamento_cursos.modules.client.dto.AuthTeacherResponseDTO;
import com.juliana.gerenciamento_cursos.modules.client.service.AuthTeacher;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class AuthTeacherController {

    private final AuthTeacher authTeacher;

    @PostMapping("/auth")
    public ResponseEntity<AuthTeacherResponseDTO> createToken(@Valid @RequestBody AuthTeacherDTO authTeacherDTO) throws AuthenticationException {
        var result = authTeacher.execute(authTeacherDTO);
        return ResponseEntity.ok(result);
    }
}
