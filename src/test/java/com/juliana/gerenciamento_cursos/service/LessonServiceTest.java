package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.DTOs.LessonDTO;
import com.juliana.gerenciamento_cursos.DTOs.request_payload.LessonRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.response.LessonResponse;
import com.juliana.gerenciamento_cursos.domain.lesson.Lesson;
import com.juliana.gerenciamento_cursos.domain.unit.Unit;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.repository.LessonRepository;
import com.juliana.gerenciamento_cursos.repository.UnitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {
    @Mock
    LessonRepository repository;

    @Mock
    UnitRepository unitRepository;

    @InjectMocks
    LessonService service;

    @Test
    @DisplayName("Cria aula com sucesso")
    void createNewClassCase1() {
        Unit unit = new Unit();
        LessonRequestPayload requestPayload = new LessonRequestPayload("Instalando java", "passo a passo de como instalar jdk", unit.getId());

        Lesson lesson = new Lesson(requestPayload.title(),
                requestPayload.description(),
                unit);

        when(unitRepository.findById(any())).thenReturn(Optional.of(unit));
        LessonResponse messageResult = service.createNewLesson(requestPayload);
        verify(repository, times(1)).save(any());
        assertEquals(lesson.getId(), messageResult.id());
    }

    @Test
    @DisplayName("Deve lançar exceção de modulo não encontrado")
    void createNewClassCase2() {
        when(unitRepository.findById(any())).thenReturn(Optional.empty());
        LessonRequestPayload requestPayload = new LessonRequestPayload("Instalando java", "passo a passo de como instalar jdk", new Unit().getId());


        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.createNewLesson(requestPayload);
        });

        assertEquals("Modulo não encontrado", thrown.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve deletar a aula com sucesso")
    void deleteClassCase1() {
        when(repository.existsById(any())).thenReturn(true);
        service.deleteLesson(any());
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("Deve lançar exceção de id não encontrado")
    void deleteClassCase2() {
        when(repository.existsById(any())).thenReturn(false);

        Exception thrown = assertThrows(InexistentOptionException.class, ()->{
            service.deleteLesson(any());
        });

        assertEquals("Modulo não encontrado", thrown.getMessage());
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve retornar lista de aulas")
    void showClassesOfModuleCase1() {
        Unit unit = new Unit();
        Lesson lesson1 = new Lesson("Instalando java", "passo a passo de como instalar jdk", unit);
        Lesson lesson2 = new Lesson("Introdução à POO", "primeiros passos na programação orientada a objetos", unit);

        List<Lesson> lessons = new ArrayList<>();
        lessons.add(lesson1);
        lessons.add(lesson2);

        when(repository.findByUnit_id(any())).thenReturn(Optional.of(lessons));

        List<LessonDTO> lessonsConverted = lessons.stream()
                .map(l -> new LessonDTO(l.getTitle(),
                        l.getDescription(),
                        l.getUnit().getTitle()))
                .toList();

        List<LessonDTO> result = service.showLessonsOfModule(any());
        assertEquals(lessonsConverted, result);
    }

    @Test
    @DisplayName("Deve lançar InexistentOptionException")
    void showClassesOfModuleCase2() {
        when(repository.findByUnit_id(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.showLessonsOfModule(any());
        });

        assertEquals("Nada encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de aulas com o respectivo titulo")
    void findClassesByTitleCase1() {
        Unit unit = new Unit();
        Lesson lesson1 = new Lesson("Instalando java", "passo a passo de como instalar jdk", unit);
        Lesson lesson2 = new Lesson("Introdução à POO", "primeiros passos na programação orientada a objetos", unit);

        List<Lesson> lessons = new ArrayList<>();
        lessons.add(lesson1);
        lessons.add(lesson2);

        when(repository.findByUnit_id(any())).thenReturn(Optional.of(lessons));

        List<LessonDTO> lessonsConverted = lessons.stream()
                .filter(l -> l.getTitle().equalsIgnoreCase("Instalando java"))
                .map(l -> new LessonDTO(l.getTitle(),
                        l.getDescription(),
                        l.getUnit().getTitle()))
                .toList();

        List<LessonDTO> result = service.findLessonsByTitle(any(), "Instalando java");
        assertEquals(lessonsConverted, result);
    }

    @Test
    @DisplayName("Deve lançar InexistentOptionException")
    void findClassesByTitleCase2() {
        when(repository.findByUnit_id(any())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.findLessonsByTitle(any(), "Introdução à POO");
        });

        assertEquals("Nada encontrado", thrown.getMessage());
    }
}