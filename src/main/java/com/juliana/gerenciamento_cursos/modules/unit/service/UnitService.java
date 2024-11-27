package com.juliana.gerenciamento_cursos.modules.unit.service;

import com.juliana.gerenciamento_cursos.modules.unit.dto.UnitDTO;
import com.juliana.gerenciamento_cursos.modules.course.entity.Course;
import com.juliana.gerenciamento_cursos.modules.unit.entity.Unit;
import com.juliana.gerenciamento_cursos.modules.unit.dto.UnitRequestPayload;
import com.juliana.gerenciamento_cursos.modules.unit.dto.UnitResponse;
import com.juliana.gerenciamento_cursos.exceptions.EmptyListException;
import com.juliana.gerenciamento_cursos.modules.course.repository.CourseRepository;
import com.juliana.gerenciamento_cursos.modules.unit.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository repository;

    private final CourseRepository courseRepository;

    public UnitResponse createNewUnit(UnitRequestPayload requestPayload){
        Course course = courseRepository.findById(requestPayload.courseId()).orElseThrow(() -> new NoSuchElementException("Curso não encontrado"));
        Unit newUnit = new Unit(requestPayload.title(), requestPayload.description(), requestPayload.difficulty(), course);
        repository.save(newUnit);

        return new UnitResponse(newUnit.getId());
    }

    public void deleteUnit(UUID id){
        validateId(id);
        repository.deleteById(id);
    }

    public List<UnitDTO> findUnitsByCourse(UUID courseId) throws NoSuchElementException, EmptyListException{
        List<UnitDTO> units = repository.findByCourseId(courseId)
                .orElseThrow(() -> new NoSuchElementException("Nenhum modulo encontrado"))
                .stream()
                .map(this::convertToDTO)
                .toList();

        if(units.isEmpty()){
            throw new EmptyListException("Não há unidades registradas nesse curso");
        }

        return units;
    }

    public List<UnitDTO> findUnitInCourse(UUID courseId, String title) throws NoSuchElementException, EmptyListException {
        List<UnitDTO> units = repository.findByCourseId(courseId)
                .orElseThrow(() -> new NoSuchElementException("Nenhum modulo encontrado"))
                .stream()
                .filter(m -> m.getTitle().equalsIgnoreCase(title))
                .map(this::convertToDTO)
                .toList();

        if(units.isEmpty()){
            throw new EmptyListException(String.format("Não há unidade '%S' nesse curso", title));
        }

        return units;
    }

    private void validateId(UUID id){
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Modulo não encontrado");
        }
    }

    private UnitDTO convertToDTO(Unit unit){
        return new UnitDTO(unit.getTitle(),
                unit.getDescription(),
                unit.getDifficulty().getName(),
                unit.getCourse().getTitle());
    }

}
