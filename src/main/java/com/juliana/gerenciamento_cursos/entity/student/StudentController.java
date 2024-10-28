package com.juliana.gerenciamento_cursos.entity.student;

import com.juliana.gerenciamento_cursos.entity.user.UserRequestPayload;
import com.juliana.gerenciamento_cursos.entity.user.UserResponse;
import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
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
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> students = service.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PostMapping("/register")
    public UserResponse createStudent(@RequestBody UserRequestPayload userRequestPayload, String description, EducationalLevel educationalLevel) {
       return service.createNewStudent(userRequestPayload, description, educationalLevel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable UUID id) throws InexistentOptionException {
        service.deleteStudent(id);
        return ResponseEntity.ok("Estudante apagado.");
    }
}
