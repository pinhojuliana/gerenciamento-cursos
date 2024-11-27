package com.juliana.gerenciamento_cursos.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.juliana.gerenciamento_cursos.DTOs.auth.AuthTeacherDTO;
import com.juliana.gerenciamento_cursos.DTOs.auth.AuthTeacherResponseDTO;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.repository.TeacherRepository;
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
public class AuthTeacher {
    //criar essa variavel depois no perfil de dev
    @Value("${security.token.secret.teacher}")
    private final String secretKey;

    private final TeacherRepository repository;

    private final PasswordEncoder passwordEncoder;

    public AuthTeacherResponseDTO execute(AuthTeacherDTO authTeacherDTO) throws AuthenticationException {
        var teacher = repository.findByUsername(authTeacherDTO.username())
                .orElseThrow(() -> new InexistentOptionException("Email ou senha incorretos"));

        boolean passwordMatches = passwordEncoder.matches(authTeacherDTO.password(), teacher.getPassword());

        if(!passwordMatches){
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(30));
        var token = JWT.create()
                .withIssuer("gestao-cursos")
                .withSubject(teacher.getId().toString())
                .withClaim("roles", List.of("TEACHER"))
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        return new AuthTeacherResponseDTO(token, expiresIn);
    }
}
