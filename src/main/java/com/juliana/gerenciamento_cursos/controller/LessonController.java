package com.juliana.gerenciamento_cursos.controller;

import com.juliana.gerenciamento_cursos.DTOs.LessonDTO;
import com.juliana.gerenciamento_cursos.DTOs.request_payload.LessonRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.response.LessonResponse;
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
    public ResponseEntity<List<LessonDTO>> showLessonsOfModule(@PathVariable UUID unitId){
        List<LessonDTO> classes = service.showLessonsOfModule(unitId);
        return ResponseEntity.ok(classes);
    }

    @PostMapping("/register")
    public LessonResponse createNewLesson(@RequestBody LessonRequestPayload requestPayload){
        return service.createNewLesson(requestPayload);
    }

    @PostMapping("/units/{id}")
    public ResponseEntity<List<LessonDTO>> findLessonsByTitle(@PathVariable UUID unitId, @RequestBody String lessonTitle){
        List<LessonDTO> lessons = service.findLessonsByTitle(unitId, lessonTitle);
        return ResponseEntity.ok(lessons);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLesson(@PathVariable UUID id){
        service.deleteLesson(id);
        return ResponseEntity.ok("Modulo deletado com sucesso");
    }
}
