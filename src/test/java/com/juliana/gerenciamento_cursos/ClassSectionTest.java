package com.juliana.gerenciamento_cursos;

import com.juliana.gerenciamento_cursos.entity.class_section.*;
import com.juliana.gerenciamento_cursos.entity.module_section.*;
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
public class ClassSectionTest {
    @InjectMocks
    ClassSectionService service;

    @Mock
    ClassSectionRepository repository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarModulo(){
        ClassSection newClass = new ClassSection();
        newClass.setId(UUID.randomUUID());
        ClassSectionRequestPayload requestPayload = new ClassSectionRequestPayload();

        when(repository.findByTitle("")).thenReturn(Optional.empty());
        when(repository.save(any(ClassSection.class))).thenReturn(newClass);

        ClassSectionResponse response = service.createClass(requestPayload);

        assertNotNull(response);
        assertEquals(newClass.getId(), response.id());
    }

    @Test
    void deveDeletarModulo(){
        ClassSection newClass = new ClassSection();
        newClass.setId(UUID.randomUUID());

        when(repository.findByTitle("")).thenReturn(Optional.of(newClass));
        when(repository.findById(newClass.getId())).thenReturn(Optional.of(newClass));
        doNothing().when(repository).deleteById(newClass.getId());

        service.deleteClass(newClass.getId());

        verify(repository).deleteById(newClass.getId());
        verify(repository, times(0)).findByTitle("");
        verify(repository, times(0)).findById(newClass.getId());
    }
