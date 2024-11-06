package com.juliana.gerenciamento_cursos.service;

import com.juliana.gerenciamento_cursos.DTOs.request_payload.LessonRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.response.LessonResponse;
import com.juliana.gerenciamento_cursos.domain.lesson.Lesson;
import com.juliana.gerenciamento_cursos.domain.unit.Unit;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import com.juliana.gerenciamento_cursos.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository repository;

    public LessonResponse createNewLesson(LessonRequestPayload lessonRequestPayload, Unit unit){
        Lesson newLesson = new Lesson(lessonRequestPayload.title(), lessonRequestPayload.description(), unit);
        repository.save(newLesson);

        return new LessonResponse(newLesson.getId());
    }

    public void deleteLesson(UUID id){
        validateId(id);
        repository.deleteById(id);
    }

    public List<Lesson> showLessonsOfModule(UUID unitId) throws InexistentOptionException {
        List<Lesson> classes = repository.findByModules_Id(unitId);
        if (classes.isEmpty()){
            throw new InexistentOptionException("Nenhuma aula encontrada");
        }
        return classes;
    }

    public List<Lesson> findLessonsByTitle(UUID unitId , String title) throws InexistentOptionException {
        List<Lesson> classes = repository.findByTitle(title).stream()
                .filter(c -> c.getUnit().getId().equals(unitId))
                .toList();
        if (classes.isEmpty()){
            throw new InexistentOptionException("Nenhuma aula encontrada");
        }
        return classes;
    }

    private void validateId(UUID id){
        if (!repository.existsById(id)) {
            throw new InexistentOptionException("Modulo não encontrado");
        }
    }

}
