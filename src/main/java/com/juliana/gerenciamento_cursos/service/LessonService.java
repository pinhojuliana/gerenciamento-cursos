package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.DTOs.LessonDTO;
import com.juliana.gerenciamento_cursos.DTOs.request_payload.LessonRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.response.LessonResponse;
import com.juliana.gerenciamento_cursos.domain.lesson.Lesson;
import com.juliana.gerenciamento_cursos.domain.unit.Unit;
import com.juliana.gerenciamento_cursos.exceptions.EmptyListException;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.repository.LessonRepository;
import com.juliana.gerenciamento_cursos.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository repository;

    private final UnitRepository unitRepository;

    public LessonResponse createNewLesson(LessonRequestPayload requestPayload){
        Unit unit = unitRepository.findById(requestPayload.unitId())
                .orElseThrow(() -> new InexistentOptionException("Modulo não encontrado"));

        Lesson newLesson = new Lesson(requestPayload.title(), requestPayload.description(), unit);

        repository.save(newLesson);

        return new LessonResponse(newLesson.getId());
    }

    public void deleteLesson(UUID id){
        validateId(id);
        repository.deleteById(id);
    }

    public List<LessonDTO> showLessonsOfModule(UUID unitId) throws InexistentOptionException, EmptyListException {
        List<LessonDTO> lessons = repository.findByUnit_id(unitId).orElseThrow(()-> new InexistentOptionException("Módulo não encontrado"))
                .stream()
                .map(this::convertToDTO)
                .toList();

        if(lessons.isEmpty()){
            throw new InexistentOptionException("Nenhuma aula encontrada para esse módulo");
        }

        return lessons;
    }

    public List<LessonDTO> findLessonsByTitle(UUID unitId , String title) throws InexistentOptionException {
        List<LessonDTO> lessons = repository.findByUnit_id(unitId).orElseThrow(()-> new InexistentOptionException("Módulo não encontrado"))
                .stream()
                .filter(l -> l.getTitle().equalsIgnoreCase(title))
                .map(this::convertToDTO)
                .toList();

        if(lessons.isEmpty()){
            throw new InexistentOptionException("Nenhuma aula encontrada");
        }

        return lessons;
    }

    private void validateId(UUID id){
        if (!repository.existsById(id)) {
            throw new InexistentOptionException("Modulo não encontrado");
        }
    }

    private LessonDTO convertToDTO(Lesson lesson){
        return new LessonDTO(lesson.getTitle(),
                lesson.getDescription(),
                lesson.getUnit().getTitle());
    }

}
