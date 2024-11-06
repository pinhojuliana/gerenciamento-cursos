package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.DTOs.UnitDTO;
import com.juliana.gerenciamento_cursos.domain.course.Course;
import com.juliana.gerenciamento_cursos.domain.unit.Unit;
import com.juliana.gerenciamento_cursos.DTOs.request_payload.UnitRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.response.UnitResponse;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.repository.CourseRepository;
import com.juliana.gerenciamento_cursos.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository repository;

    private final CourseRepository courseRepository;

    public UnitResponse createNewUnit(UnitRequestPayload requestPayload){
        Course course = courseRepository.findById(requestPayload.courseId()).orElseThrow(() -> new InexistentOptionException("Curso não encontrado"));
        Unit newUnit = new Unit(requestPayload.title(), requestPayload.description(), requestPayload.difficulty(), course);
        repository.save(newUnit);

        return new UnitResponse(newUnit.getId());
    }

    public void deleteUnit(UUID id){
        validateId(id);
        repository.deleteById(id);
    }

    public List<UnitDTO> findUnitsByCourse(UUID courseId){
        List<Unit> units = repository.findByCourseId(courseId)
               .orElseThrow(() -> new InexistentOptionException("Nenhum modulo encontrado"));

        return units.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<UnitDTO> findUnitCourse(UUID courseId, String title) throws InexistentOptionException {
        List<Unit> units = repository.findByCourseId(courseId)
                .orElseThrow(() -> new InexistentOptionException("Nenhum modulo encontrado"));

        return units.stream()
                .filter(m -> m.getTitle().equalsIgnoreCase(title))
                .map(this::convertToDTO)
                .toList();
    }

    private void validateId(UUID id){
        if (!repository.existsById(id)) {
            throw new InexistentOptionException("Modulo não encontrado");
        }
    }

    private UnitDTO convertToDTO(Unit unit){
        return new UnitDTO(unit.getTitle(),
                unit.getDescription(),
                unit.getDifficulty().getName(),
                unit.getCourse().getTitle());
    }

}
