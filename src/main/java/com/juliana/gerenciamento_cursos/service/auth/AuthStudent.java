package com.juliana.gerenciamento_cursos.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.juliana.gerenciamento_cursos.DTOs.auth.AuthStudentDTO;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthStudent {
    @Value("${security.token.secret}")
    private final String secretKey;

    private final StudentRepository repository;

    private final PasswordEncoder passwordEncoder;

    public String execute(AuthStudentDTO authStudentDTO) throws AuthenticationException {
        var student = repository.findByEmail(authStudentDTO.email())
                .orElseThrow(() -> new InexistentOptionException("E-mail ou senha incorretos"));

        boolean passowrMatches = passwordEncoder.matches(authStudentDTO.password(), student.getPassword());

        if(!passowrMatches){
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withIssuer("gerenciamento_cursos")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(student.getId().toString())
                .sign(algorithm);
    }
}
