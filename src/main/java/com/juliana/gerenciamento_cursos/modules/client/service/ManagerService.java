package com.juliana.gerenciamento_cursos.modules.client.service;

import com.juliana.gerenciamento_cursos.exceptions.EmailAlreadyInUseException;
import com.juliana.gerenciamento_cursos.exceptions.UsernameAlreadyInUseException;
import com.juliana.gerenciamento_cursos.modules.client.dto.ManagerRequestPayload;
import com.juliana.gerenciamento_cursos.modules.client.dto.ManagerResponse;
import com.juliana.gerenciamento_cursos.modules.client.entity.Manager;
import com.juliana.gerenciamento_cursos.modules.client.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

import static com.juliana.gerenciamento_cursos.util.AgeValidation.validateAge;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository repository;

    public ManagerResponse createManager(ManagerRequestPayload requestPayload){
        if (repository.existsByUsername(requestPayload.username())) {
            throw new UsernameAlreadyInUseException("Esse username já está sendo utilizado por outro usuário");
        }
        if (repository.existsByEmail(requestPayload.email())) {
            throw new EmailAlreadyInUseException("Esse e-mail já está sendo utilizado por outro usuário");
        }

        validateAge(requestPayload.dateOfBirth());

        var manager = Manager.builder()
                .name(requestPayload.name())
                .email(requestPayload.email())
                .password(requestPayload.password())
                .username(requestPayload.username())
                .dateOfBirth(requestPayload.dateOfBirth())
                .designation(requestPayload.designation())
                .build();

        repository.save(manager);
        return new ManagerResponse(manager.getId());
    }

    public void removeManager(UUID id){
        if(!repository.existsById(id)){
            throw new NoSuchElementException("Ese usuário não existe");
        }
        repository.deleteById(id);
    }
}
