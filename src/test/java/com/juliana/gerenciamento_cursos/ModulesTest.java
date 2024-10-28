package com.juliana.gerenciamento_cursos;

import com.juliana.gerenciamento_cursos.entity.modules.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ModulesTest {
    /* @InjectMocks
    ModulesService service;

    @Mock
    ModulesRepository repository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarModulo(){
        Modules module = new Modules();
        module.setId(UUID.randomUUID());
        ModulesRequestPayload requestPayload = new ModulesRequestPayload();

        when(repository.findByTitle("")).thenReturn(Optional.empty());
        when(repository.save(any(Modules.class))).thenReturn(module);

        ModulesResponse response = service.(requestPayload);

        assertNotNull(response);
        assertEquals(module.getId(), response.id());
    }

    @Test
    void deveDeletarModulo(){
        Modules module = new Modules();
        module.setId(UUID.randomUUID());

        when(repository.findByTitle("")).thenReturn(Optional.of(module));
        when(repository.findById(module.getId())).thenReturn(Optional.of(module));
        doNothing().when(repository).deleteById(module.getId());

        service.deleteModule(module.getId());

        verify(repository).deleteById(module.getId());
        verify(repository, times(0)).findByTitle("");
        verify(repository, times(0)).findById(module.getId());
    }

     */
}
