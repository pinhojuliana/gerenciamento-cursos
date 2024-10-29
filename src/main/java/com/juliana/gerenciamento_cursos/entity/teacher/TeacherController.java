package com.juliana.gerenciamento_cursos.entity.teacher;

import com.juliana.gerenciamento_cursos.entity.client.ClientRequestPayload;
import com.juliana.gerenciamento_cursos.entity.client.ClientResponse;
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
    public ClientResponse createTeacher(@RequestBody ClientRequestPayload clientRequestPayload) {
        return service.createNewTeacher(clientRequestPayload);
    }

    @PutMapping("/{id}/add-skill")
    public ResponseEntity<String> addSkill(@PathVariable UUID id, @RequestBody String skill){
        service.addSkill(id, skill);
        return ResponseEntity.ok("Skill adicionada com sucesso");
    }

    @PutMapping("/{id}/username")
    public ResponseEntity<String> updateTeacherUsername(@PathVariable UUID id, @RequestBody String username){
        service.updateTeacherUsername(id, username);
        return ResponseEntity.ok("Nome de usuario atualizado com sucesso");
    }

    @PutMapping("/{id}/email")
    public ResponseEntity<String> updateTeacherEmail(@PathVariable UUID id, @RequestBody String email){
        service.updateTeacherEmail(id, email);
        return ResponseEntity.ok("Email atualizado com sucesso");
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<String> updateTeacherPassword(@PathVariable UUID id, @RequestBody String oldPassword, @RequestBody String newPassword){
        service.updateTeacherPassword(id, oldPassword,newPassword);
        return ResponseEntity.ok("Senha atualizada com sucesso");
    }

    @DeleteMapping("/{id}/remove-skill")
    public ResponseEntity<String> removeSkill(@PathVariable UUID id, @RequestBody String skill){
        service.removeSkill(id, skill);
        return ResponseEntity.ok("Skill removida com sucesso");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable UUID id) {
        service.deleteTeacher(id);
        return ResponseEntity.ok("Estudante apagado.");
    }
}
