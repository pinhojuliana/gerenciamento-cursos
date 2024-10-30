package com.juliana.gerenciamento_cursos.entity.student;

import com.juliana.gerenciamento_cursos.entity.client.ClientRequestPayload;
import com.juliana.gerenciamento_cursos.entity.client.ClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        List<StudentDTO> students = service.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<StudentDTO>> getStudentsByName(@PathVariable String name){
        List<StudentDTO> students = service.searchStudentName(name);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable UUID id){
        Student student = service.searchStudent(id);
        return ResponseEntity.ok(student);
    }

    @PostMapping("/register")
    public ClientResponse createStudent(@RequestBody ClientRequestPayload clientRequestPayload, String description, EducationalLevel educationalLevel) {
       return service.createNewStudent(clientRequestPayload, description, educationalLevel);
    }

    @PutMapping("/{id}/description")
    public ResponseEntity<String> alterStudentDescription(@PathVariable UUID id, @RequestBody String description){
        service.updateStudentDescription(id, description);
        return ResponseEntity.ok("Descrição atualizada com sucesso");
    }

    @PutMapping("/{id}/education-level")
    public ResponseEntity<String> alterStudentEducationalLevel(@PathVariable  UUID id, @RequestBody EducationalLevel educationalLevel){
        service.updateStudentEducationalLevel(id, educationalLevel);
        return ResponseEntity.ok("Nivel de escolaridade atualizado com sucesso");
    }

    @PutMapping("/{id}/username")
    public ResponseEntity<String> alterStudentUsername(@PathVariable UUID id, @RequestBody String username){
        service.updateStudentUsername(id, username);
        return ResponseEntity.ok("Nome de usuario atualizado com sucesso");
    }

    @PutMapping("/{id}/email")
    public ResponseEntity<String> alterStudentEmail(@PathVariable UUID id, @RequestBody String email){
        service.updateStudentEmail(id, email);
        return ResponseEntity.ok("Email atualizado com sucesso");
    }

    @PutMapping(("/{id}/password"))
    public ResponseEntity<String> alterStudentPassword(@PathVariable UUID id, @RequestBody String oldPassword, String newPassword){
        service.updateStudentPassword(id, oldPassword,newPassword);
        return ResponseEntity.ok("Senha atualizada com sucesso");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable UUID id) {
        service.deleteStudent(id);
        return ResponseEntity.ok("Estudante apagado.");
    }
}
