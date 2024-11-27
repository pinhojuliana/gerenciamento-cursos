package com.juliana.gerenciamento_cursos.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.juliana.gerenciamento_cursos.DTOs.auth.AuthStudentDTO;
import com.juliana.gerenciamento_cursos.DTOs.auth.AuthStudentResponseDTO;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthStudent {
    @Value("${security.token.secret.student}")
    private final String secretKey;

    private final StudentRepository repository;

    private final PasswordEncoder passwordEncoder;

    public AuthStudentResponseDTO execute(AuthStudentDTO authStudentDTO) throws AuthenticationException {
        var student = repository.findByUsername(authStudentDTO.username())
                .orElseThrow(() -> new InexistentOptionException("Username ou senha incorretos"));

        boolean passowrMatches = passwordEncoder.matches(authStudentDTO.password(), student.getPassword());

        if(!passowrMatches){
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(30));
        var token = JWT.create()
                .withIssuer("gestao-vagas")
                .withSubject(student.getId().toString())
                .withClaim("roles", List.of("STUDENT"))
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        return new AuthStudentResponseDTO(token, expiresIn);
    }
}
