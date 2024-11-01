package com.juliana.gerenciamento_cursos.controller;

import com.juliana.gerenciamento_cursos.domain.class_section.ClassSection;
import com.juliana.gerenciamento_cursos.DTOs.ClassSectionRequestPayload;
import com.juliana.gerenciamento_cursos.DTOs.ClassSectionResponse;
import com.juliana.gerenciamento_cursos.service.ClassSectionService;
import com.juliana.gerenciamento_cursos.domain.modules.Modules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/classes")
public class ClassSectionController {
    @Autowired
    ClassSectionService service;

    @GetMapping("/modules/{id}")
    public ResponseEntity<List<ClassSection>> showClassSectionOfModule(@PathVariable UUID moduleId){
        List<ClassSection> classes = service.showClassesOfModule(moduleId);
        return ResponseEntity.ok(classes);
    }

    @PostMapping("/register")
    public ClassSectionResponse createNewModule(@RequestBody ClassSectionRequestPayload requestPayload, Modules module){
        return service.createNewClass(requestPayload, module);
    }

    @PostMapping("/modules/{id}")
    public ResponseEntity<List<ClassSection>> findClassesByTitle(@PathVariable UUID moduleId, @RequestBody String classTitle){
        List<ClassSection> classes = service.findClassesByTitle(moduleId, classTitle);
        return ResponseEntity.ok(classes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClass(@PathVariable UUID id){
        service.deleteClass(id);
        return ResponseEntity.ok("Modulo deletado com sucesso");
    }
}
