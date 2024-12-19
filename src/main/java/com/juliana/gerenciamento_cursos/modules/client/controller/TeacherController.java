package com.juliana.gerenciamento_cursos.modules.client.controller;

import com.juliana.gerenciamento_cursos.modules.course.dto.CourseDTO;
import com.juliana.gerenciamento_cursos.modules.update_request.EmailUpdateRequest;
import com.juliana.gerenciamento_cursos.modules.update_request.PasswordUpdateRequest;
import com.juliana.gerenciamento_cursos.modules.update_request.SkillUpdateRequest;
import com.juliana.gerenciamento_cursos.modules.update_request.UsernameUpdateRequest;
import com.juliana.gerenciamento_cursos.modules.client.dto.TeacherRequestPayload;
import com.juliana.gerenciamento_cursos.modules.client.dto.ClientResponse;
import com.juliana.gerenciamento_cursos.modules.client.dto.TeacherDTO;
import com.juliana.gerenciamento_cursos.modules.client.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    TeacherService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TeacherDTO>> getAllTeachers(){
        List<TeacherDTO> teachers = service.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TeacherDTO>> findTeacherByName(@RequestParam String name){
        List<TeacherDTO> teachers = service.findTeacherByName(name);
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}/courses-taught")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Set<CourseDTO>> showAllCoursesTaught(@PathVariable UUID id){
        Set<CourseDTO> courses = service.showAllCoursesTaught(id);
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse createTeacher(@RequestBody TeacherRequestPayload teacherRequestPayload) {
        return service.createNewTeacher(teacherRequestPayload);
    }

    @PutMapping("/{id}/add-skill")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> addSkill(@PathVariable UUID id, @Valid @RequestBody SkillUpdateRequest skillUpdateRequest){
        service.addSkill(id, skillUpdateRequest.skill());
        return ResponseEntity.ok("Skill adicionada com sucesso");
    }

    @PutMapping("/{id}/remove-skill")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeSkill(@PathVariable UUID id, @Valid @RequestBody SkillUpdateRequest skillUpdateRequest){
        service.removeSkill(id, skillUpdateRequest.skill());
        return ResponseEntity.ok("Skill removida com sucesso");
    }

    @PutMapping("/{id}/username")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateTeacherUsername(@PathVariable UUID id, @Valid @RequestBody UsernameUpdateRequest usernameUpdateRequest){
        service.updateTeacherUsername(id, usernameUpdateRequest.username());
        return ResponseEntity.ok("Nome de usuario atualizado com sucesso");
    }

    @PutMapping("/{id}/email")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateTeacherEmail(@PathVariable UUID id,@Valid @RequestBody EmailUpdateRequest emailUpdateRequest){
        service.updateTeacherEmail(id, emailUpdateRequest.email());
        return ResponseEntity.ok("Email atualizado com sucesso");
    }

    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateTeacherPassword(@PathVariable UUID id, @Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest){
        service.updateTeacherPassword(id, passwordUpdateRequest.oldPassword(), passwordUpdateRequest.newPassword());
        return ResponseEntity.ok("Senha atualizada com sucesso");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteTeacher(@PathVariable UUID id) {
        service.deleteTeacher(id);
        return ResponseEntity.ok("Professor apagado.");
    }
}
