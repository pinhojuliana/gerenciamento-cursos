package com.juliana.gerenciamento_cursos.controller;

import com.juliana.gerenciamento_cursos.domain.course.Course;
import com.juliana.gerenciamento_cursos.DTOs.CourseRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.CourseResponse;
import com.juliana.gerenciamento_cursos.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    CourseService service;

    @GetMapping
    public ResponseEntity<List<Course>> showCourses(){
        List<Course> courses = service.showCourses();
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/register")
    public CourseResponse createNewCourse(@RequestBody CourseRequestPayload requestPayload){
        return service.createNewCourse(requestPayload);
    }

    @PutMapping("/{id}/description")
    public ResponseEntity<String> alterDescription(@PathVariable UUID id, @RequestBody String description){
        service.alterDescription(id, description);
        return ResponseEntity.ok("Descrição atualizada com sucesso");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable UUID id){
        service.deleteCourse(id);
        return ResponseEntity.ok("Curso apagado com sucesso");
    }
}
