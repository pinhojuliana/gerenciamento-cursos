package com.juliana.gerenciamento_cursos.modules.lesson.service;

import com.juliana.gerenciamento_cursos.modules.lesson.dto.LessonDTO;
import com.juliana.gerenciamento_cursos.modules.lesson.dto.LessonRequestPayload;
import com.juliana.gerenciamento_cursos.modules.lesson.dto.LessonResponse;
import com.juliana.gerenciamento_cursos.modules.lesson.entity.Lesson;
import com.juliana.gerenciamento_cursos.modules.unit.entity.Unit;
import com.juliana.gerenciamento_cursos.modules.lesson.repository.LessonRepository;
import com.juliana.gerenciamento_cursos.modules.unit.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository repository;

    private final UnitRepository unitRepository;

    public LessonResponse createNewLesson(LessonRequestPayload requestPayload){
        Unit unit = unitRepository.findById(requestPayload.unitId())
                .orElseThrow(() -> new NoSuchElementException("Modulo não encontrado"));

        var lesson = Lesson.builder()
                .title(requestPayload.title())
                .description(requestPayload.description())
                .unit(unit)
                .build();

        repository.save(lesson);

        return new LessonResponse(lesson.getId());
    }

    public void deleteLesson(UUID id){
        validateId(id);
        repository.deleteById(id);
    }

    public List<LessonDTO> showLessonsOfModule(UUID unitId) throws NoSuchElementException {
        List<LessonDTO> lessons = repository.findByUnit_id(unitId).orElseThrow(()-> new NoSuchElementException("Módulo não encontrado"))
                .stream()
                .map(this::convertToDTO)
                .toList();

        if(lessons.isEmpty()){
            throw new NoSuchElementException("Nenhuma aula encontrada para esse módulo");
        }

        return lessons;
    }

    public List<LessonDTO> findLessonsByTitle(String title) throws NoSuchElementException {
        return repository.findByTitleContainsIgnoreCase(title)
                .orElseThrow(() -> new NoSuchElementException("Nenhuma aula encontrada."))
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    private void validateId(UUID id){
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Modulo não encontrado");
        }
    }

    private LessonDTO convertToDTO(Lesson lesson){
        return new LessonDTO(lesson.getTitle(),
                lesson.getDescription(),
                lesson.getUnit().getTitle());
    }

}
