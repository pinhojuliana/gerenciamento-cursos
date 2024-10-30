package com.juliana.gerenciamento_cursos.entity.modules;

import com.juliana.gerenciamento_cursos.entity.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/modules")
public class ModulesController {
    @Autowired
    ModulesService service;

    @GetMapping("/course/{id}")
    public ResponseEntity<List<Modules>> findModulesByCourse(@PathVariable UUID courseId){
        List<Modules> modules = service.findModulesByCourse(courseId);
        return ResponseEntity.ok(modules);
    }

    @PostMapping("/register")
    public ModulesResponse createNewModule(@RequestBody ModulesRequestPayload moduleRequestPayload, Course course){
        return service.createNewModule(moduleRequestPayload, course);
    }

    @PostMapping("/course/{id}")
    public ResponseEntity<List<Modules>> findModulesCourse(@PathVariable UUID courseId, @RequestBody String moduleTitle){
        List<Modules> modules = service.findModuleCourse(courseId, moduleTitle);
        return ResponseEntity.ok(modules);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteModule(@PathVariable UUID id){
        service.deleteModule(id);
        return ResponseEntity.ok("Modulo deletado com sucesso");
    }
}
