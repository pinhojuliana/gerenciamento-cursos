package com.juliana.gerenciamento_cursos.modules.unit.controller;

import com.juliana.gerenciamento_cursos.modules.unit.dto.UnitDTO;
import com.juliana.gerenciamento_cursos.modules.unit.dto.UnitRequestPayload;
import com.juliana.gerenciamento_cursos.modules.unit.dto.UnitResponse;
import com.juliana.gerenciamento_cursos.modules.unit.service.UnitService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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

    @GetMapping("/course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UnitDTO>> showUnitsOfCourse(@PathVariable UUID courseId){
        List<UnitDTO> modules = service.findUnitsByCourse(courseId);
        return ResponseEntity.ok(modules);
    }

    @GetMapping("/course/{courseId}/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UnitDTO>> findUnitsByCourse(@PathVariable UUID courseId, @RequestParam @NotEmpty String moduleTitle){
        List<UnitDTO> modules = service.findUnitInCourse(courseId, moduleTitle);
        return ResponseEntity.ok(modules);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UnitResponse createNewUnit(@Valid @RequestBody UnitRequestPayload requestPayload){
        return service.createNewUnit(requestPayload);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteUnit(@PathVariable UUID id){
        service.deleteUnit(id);
        return ResponseEntity.ok("Modulo deletado com sucesso");
    }
}
