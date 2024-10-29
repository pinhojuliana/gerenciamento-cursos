package com.juliana.gerenciamento_cursos.application.entity.teacher;

import com.juliana.gerenciamento_cursos.application.entity.user.UserRequestPayload;
import com.juliana.gerenciamento_cursos.application.entity.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    TeacherService service;

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers(){
        List<Teacher> teachers = service.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<Teacher>> getTeachersByName(@PathVariable String name){
        List<Teacher> teachers = service.findTeacher(name);
        return ResponseEntity.ok(teachers);
    }

    @PostMapping("/register")
    public UserResponse createTeacher(@RequestBody UserRequestPayload userRequestPayload) {
        return service.createNewTeacher(userRequestPayload);
    }

    @PutMapping
    public ResponseEntity<String> addSkill(@RequestBody UUID id, String skill){
        service.addSkill(id, skill);
        return ResponseEntity.ok("Skill adicionada com sucesso");
    }

    @PutMapping
    public ResponseEntity<String> updateTeacherUsername(@RequestBody UUID id, String username){
        service.updateTeacherUsername(id, username);
        return ResponseEntity.ok("Nome de usuario atualizado com sucesso");
    }

    @PutMapping
    public ResponseEntity<String> updateTeacherEmail(@RequestBody UUID id, String email){
        service.updateTeacherEmail(id, email);
        return ResponseEntity.ok("Email atualizado com sucesso");
    }

    @PutMapping
    public ResponseEntity<String> updateTeacherPassword(@RequestBody UUID id, String oldPassword, String newPassword){
        service.updateTeacherPassword(id, oldPassword,newPassword);
        return ResponseEntity.ok("Senha atualizada com sucesso");
    }

    @DeleteMapping
    public ResponseEntity<String> removeSkill(@RequestBody UUID id, String skill){
        service.removeSkill(id, skill);
        return ResponseEntity.ok("Skill removida com sucesso");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable UUID id) {
        service.deleteTeacher(id);
        return ResponseEntity.ok("Estudante apagado.");
    }
}
