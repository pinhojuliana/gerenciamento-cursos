package com.juliana.gerenciamento_cursos.modules.course.controller;

import com.juliana.gerenciamento_cursos.modules.course.dto.CourseDTO;
import com.juliana.gerenciamento_cursos.modules.client.dto.TeacherDTO;
import com.juliana.gerenciamento_cursos.modules.course.dto.CourseRequestPayload;
import com.juliana.gerenciamento_cursos.modules.course.dto.CourseResponse;
import com.juliana.gerenciamento_cursos.modules.update_request.DescriptionUpdateRequest;
import com.juliana.gerenciamento_cursos.modules.course.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    CourseService service;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CourseDTO>> showCourses(){
        List<CourseDTO> courses = service.showAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CourseDTO> findCourseByTitle(@RequestParam String title){
       CourseDTO course = service.findCourseByTitle(title);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/{id}/teachers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Set<TeacherDTO>> showTeachersOfCourse(@PathVariable UUID id){
        Set<TeacherDTO> teachers = service.showTeachersOfCourse(id);
        return ResponseEntity.ok(teachers);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse createNewCourse(@Valid @RequestBody CourseRequestPayload requestPayload){
        return service.createNewCourse(requestPayload);
    }

    @PutMapping("/description/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> alterDescription(@PathVariable UUID id, @Valid @RequestBody DescriptionUpdateRequest descriptionUpdateRequest){
        service.alterDescription(id, descriptionUpdateRequest.description());
        return ResponseEntity.ok("Descrição atualizada com sucesso");
    }

    @PutMapping("/{courseId}/teachers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> addTeacher(@RequestBody UUID teacherId, @PathVariable UUID courseId){
        service.addTeacher(teacherId, courseId);
        return ResponseEntity.ok("Professor adicionado");
    }

    @DeleteMapping("/{courseId}/teachers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeTeacher(@RequestBody UUID teacherId, @PathVariable UUID courseId){
        service.removeTeacher(teacherId, courseId);
        return ResponseEntity.ok("Professor removido");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteCourse(@PathVariable UUID id){
        service.deleteCourse(id);
        return ResponseEntity.ok("Curso apagado.");
    }
}
