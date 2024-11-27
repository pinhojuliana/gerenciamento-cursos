package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.modules.unit.dto.UnitDTO;
import com.juliana.gerenciamento_cursos.modules.unit.dto.UnitRequestPayload;
import com.juliana.gerenciamento_cursos.modules.unit.dto.UnitResponse;
import com.juliana.gerenciamento_cursos.modules.course.entity.Course;
import com.juliana.gerenciamento_cursos.modules.unit.entity.Difficulty;
import com.juliana.gerenciamento_cursos.modules.unit.entity.Unit;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.modules.course.repository.CourseRepository;
import com.juliana.gerenciamento_cursos.modules.unit.repository.UnitRepository;
import com.juliana.gerenciamento_cursos.modules.unit.service.UnitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {
    @Mock
    UnitRepository repository;

    @Mock
    CourseRepository courseRepository;

    @InjectMocks
    UnitService service;

    @Test
    @DisplayName("Deve criar um módulo com sucesso")
    void createNewUnitCase1() {
        Course course = new Course("Java", "Faça aplicções utilizando java e Spring boot");
        UnitRequestPayload requestPayload = new UnitRequestPayload("Fundamentos java", "aprenda sobre os tipos e classes", Difficulty.BEGINNER, course.getId());

        Unit unit = new Unit(requestPayload.title(),
                requestPayload.description(),
                requestPayload.difficulty(),
                course);

        when(courseRepository.findById(any())).thenReturn(Optional.of(course));
        when(repository.save(any())).thenReturn(unit);
        UnitResponse messageResult = service.createNewUnit(requestPayload);
        verify(repository, times(1)).save(any());
        assertEquals(unit.getId(), messageResult.id());
    }

    @Test
    @DisplayName("Deve lançar exceção curso não encontrado")
    void createNewUnitCase2() {
        Course course = new Course("Java", "Faça aplicções utilizando java e Spring boot");
        UnitRequestPayload requestPayload = new UnitRequestPayload("Fundamentos java", "aprenda sobre os tipos e classes", Difficulty.BEGINNER, course.getId());

        when(courseRepository.findById(any())).thenReturn(Optional.empty());
        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.createNewUnit(requestPayload);
        });

        verify(repository, never()).save(any());
        assertEquals("Curso não encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve deletar módulo com sucesso")
    void deleteUnitCase1() {
        Course course = new Course("Java", "Faça aplicções utilizando java e Spring boot");
        Unit unit = new Unit("Fundamentos java", "aprenda sobre os tipos e classes", Difficulty.BEGINNER, course);

        when(repository.existsById(any())).thenReturn(true);
        service.deleteUnit(any());
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("Deve lançar excessão id não encontrado")
    void deleteUnitCase2() {
        when(repository.existsById(any())).thenReturn(false);

        Exception thrown = assertThrows(InexistentOptionException.class, () -> {
            service.deleteUnit(any());
        });

        assertEquals("Modulo não encontrado", thrown.getMessage());
        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve retornar lista de todos os módulos de um curso")
    void findUnitsByCourseCase1() {
        Course course = new Course("Java", "Faça aplicções utilizando java e Spring boot");
        Unit unit = new Unit("Fundamentos java", "aprenda sobre os tipos e classes", Difficulty.BEGINNER, course);
        Unit unit2 = new Unit("Fundamentos Spring", "aprenda como criar aplicações com mais produtividade usando o framework Spring", Difficulty.INTERMEDIATE, course);
        List<Unit> units = new ArrayList<>();
        units.add(unit);
        units.add(unit2);
        List<UnitDTO> unitsConverted = units.stream().map(u -> new UnitDTO(u.getTitle(),
                u.getDescription(),
                u.getDifficulty().getName(),
                u.getCourse().getTitle())).toList();

        when(repository.findByCourseId(any())).thenReturn(Optional.of(units));

        List<UnitDTO> result = service.findUnitsByCourse(any());
        assertEquals(unitsConverted, result);
    }

    @Test
    @DisplayName("Deve lançar InexistentOptionException")
    void findUnitsByCourseCase2() {
       when(repository.findByCourseId(any())).thenThrow(new InexistentOptionException("Nenhum modulo encontrado"));

       assertThrows(InexistentOptionException.class, () -> {
            service.findUnitsByCourse(any());
       });
    }

    @Test
    @DisplayName("Deve retornar lista de todos os modulos com aquele titulo")
    void findUnitInCourseCase1() {
        Course course = new Course("Java", "Faça aplicções utilizando java e Spring boot");
        Unit unit = new Unit("Fundamentos java", "aprenda sobre os tipos e classes", Difficulty.BEGINNER, course);
        Unit unit2 = new Unit("Fundamentos Spring", "aprenda como criar aplicações com mais produtividade usando o framework Spring", Difficulty.INTERMEDIATE, course);

        List<Unit> units = new ArrayList<>();
        units.add(unit);
        units.add(unit2);

        List<UnitDTO> unitsConverted = units.stream().
                filter(u -> u.getTitle().equalsIgnoreCase("Fundamentos java"))
                .map(u -> new UnitDTO(u.getTitle(),
                    u.getDescription(),
                    u.getDifficulty().getName(),
                    u.getCourse().getTitle())).toList();

        when(repository.findByCourseId(any())).thenReturn(Optional.of(units));
        List<UnitDTO> result = service.findUnitInCourse(any(), "Fundamentos java");
        assertEquals(unitsConverted, result);
    }

    @Test
    @DisplayName("Deve lançar InexistentOptionException")
    void findUnitInCourseCase2() {
        when(repository.findByCourseId(any())).thenThrow(new InexistentOptionException("Nenhum modulo encontrado"));

        assertThrows(InexistentOptionException.class, () -> {
            service.findUnitInCourse(any(), "Java");
        });
    }

}