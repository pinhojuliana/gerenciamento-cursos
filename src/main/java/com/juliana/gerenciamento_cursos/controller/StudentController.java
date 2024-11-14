package com.juliana.gerenciamento_cursos.controller;

import com.juliana.gerenciamento_cursos.DTOs.update_request.*;
import com.juliana.gerenciamento_cursos.DTOs.request_payload.StudentRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.response.ClientResponse;
import com.juliana.gerenciamento_cursos.DTOs.StudentDTO;
import com.juliana.gerenciamento_cursos.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    StudentService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        List<StudentDTO> students = service.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/search/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<StudentDTO>> searchStudentByName(@PathVariable String name){
        List<StudentDTO> students = service.searchStudentName(name);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StudentDTO> searchStudentById(@PathVariable UUID id){
        StudentDTO student = service.searchStudentId(id);
        return ResponseEntity.ok(student);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse createNewStudent(@Valid @RequestBody StudentRequestPayload requestPayload) {
       return service.createNewStudent(requestPayload);
    }

    @PutMapping("/{id}/description")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateStudentDescription(@PathVariable UUID id, @Valid @RequestBody DescriptionUpdateRequest descriptionUpdateRequest){
        service.updateStudentDescription(id, descriptionUpdateRequest.description());
        return ResponseEntity.ok("Descrição atualizada com sucesso");
    }

    @PutMapping("/{id}/education-level")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateStudentEducationalLevel(@PathVariable  UUID id, @Valid @RequestBody EducationalLevelUpdateRequest educationalLevelUpdateRequest){
        service.updateStudentEducationalLevel(id, educationalLevelUpdateRequest.educationalLevel());
        return ResponseEntity.ok("Nivel de escolaridade atualizado com sucesso");
    }

    @PutMapping("/{id}/username")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateStudentUsername(@PathVariable UUID id, @Valid @RequestBody UsernameUpdateRequest usernameUpdateRequest){
        service.updateStudentUsername(id, usernameUpdateRequest.username());
        return ResponseEntity.ok("Nome de usuario atualizado com sucesso");
    }

    @PutMapping("/{id}/email")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateStudentEmail(@PathVariable UUID id, @Valid @RequestBody EmailUpdateRequest emailUpdateRequest){
        service.updateStudentEmail(id, emailUpdateRequest.email());
        return ResponseEntity.ok("Email atualizado com sucesso");
    }

    @PutMapping(("/{id}/password"))
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateStudentPassword(@PathVariable UUID id, @Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest){
        service.updateStudentPassword(id, passwordUpdateRequest.oldPassword(), passwordUpdateRequest.newPassword());
        return ResponseEntity.ok("Senha atualizada com sucesso");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteStudent(@PathVariable UUID id) {
        service.deleteStudent(id);
        return ResponseEntity.ok("Estudante apagado.");
    }
}
