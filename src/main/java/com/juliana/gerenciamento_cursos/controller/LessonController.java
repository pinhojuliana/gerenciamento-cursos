package com.juliana.gerenciamento_cursos.controller;

import com.juliana.gerenciamento_cursos.domain.lesson.Lesson;
import com.juliana.gerenciamento_cursos.DTOs.request_payload.LessonRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.response.LessonResponse;
import com.juliana.gerenciamento_cursos.domain.unit.Unit;
import com.juliana.gerenciamento_cursos.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    @Autowired
    LessonService service;

    @GetMapping("/units/{id}")
    public ResponseEntity<List<Lesson>> showLessonsOfModule(@PathVariable UUID unitId){
        List<Lesson> classes = service.showLessonsOfModule(unitId);
        return ResponseEntity.ok(classes);
    }

    @PostMapping("/register")
    public LessonResponse createNewLesson(@RequestBody LessonRequestPayload requestPayload, Unit module){
        return service.createNewLesson(requestPayload, module);
    }

    @PostMapping("/units/{id}")
    public ResponseEntity<List<Lesson>> findLessonsByTitle(@PathVariable UUID unitId, @RequestBody String lessonTitle){
        List<Lesson> lessons = service.findLessonsByTitle(unitId, lessonTitle);
        return ResponseEntity.ok(lessons);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLesson(@PathVariable UUID id){
        service.deleteLesson(id);
        return ResponseEntity.ok("Modulo deletado com sucesso");
    }
}
