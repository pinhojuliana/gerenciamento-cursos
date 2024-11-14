package com.juliana.gerenciamento_cursos.controller;

import com.juliana.gerenciamento_cursos.DTOs.UnitDTO;
import com.juliana.gerenciamento_cursos.DTOs.request_payload.UnitRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.response.UnitResponse;
import com.juliana.gerenciamento_cursos.service.UnitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/units")
public class UnitController {
    @Autowired
    UnitService service;

    @GetMapping("/course/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UnitDTO>> findUnitsByCourse(@PathVariable UUID courseId){
        List<UnitDTO> modules = service.findUnitsByCourse(courseId);
        return ResponseEntity.ok(modules);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UnitResponse createNewUnit(@Valid @RequestBody UnitRequestPayload requestPayload){
        return service.createNewUnit(requestPayload);
    }

    @PostMapping("/course/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UnitDTO>> findUnitsCourse(@PathVariable UUID courseId, @RequestBody String moduleTitle){
        List<UnitDTO> modules = service.findUnitInCourse(courseId, moduleTitle);
        return ResponseEntity.ok(modules);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteUnit(@PathVariable UUID id){
        service.deleteUnit(id);
        return ResponseEntity.ok("Modulo deletado com sucesso");
    }
}
