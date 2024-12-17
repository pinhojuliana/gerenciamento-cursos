package com.juliana.gerenciamento_cursos.modules.course.controller;

import com.juliana.gerenciamento_cursos.modules.course.dto.CourseDTO;
import com.juliana.gerenciamento_cursos.modules.client.dto.TeacherDTO;
import com.juliana.gerenciamento_cursos.modules.course.dto.CourseRequestPayload;
import com.juliana.gerenciamento_cursos.modules.course.dto.CourseResponse;
import com.juliana.gerenciamento_cursos.modules.update_request.DescriptionUpdateRequest;
import com.juliana.gerenciamento_cursos.modules.course.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    CourseService service;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Mostrar cursos",
            description = "Função responsável por listar todos os cursos disponíveis na plataforma")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Lista de cursos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CourseDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Mensagem de erro: 'Esse curso já existe'"
            )
    })
    public ResponseEntity<List<CourseDTO>> showCourses(){
        List<CourseDTO> courses = service.showAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Procurar cursos por título",
    description = "Função responsável por pesquisar um curso através do título")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de cursos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CourseDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mensagem de erro:'Nenhum curso encontrado'"
            )
    })
    public ResponseEntity<CourseDTO> findCourseByTitle(@RequestParam String title){
        CourseDTO course = service.findCourseByTitle(title);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/{id}/teachers")
    @PreAuthorize("hasRole('MANAGER', 'TEACHER', 'STUDENT')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Mostrar professores de um curso",
            description = "Função responsável por retornar uma lista com todos os professores de um curso")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de professores",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TeacherDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mensagem de erro:'O usuário ? não leciona no curso ?'"
            )
    })
    public ResponseEntity<Set<TeacherDTO>> showTeachersOfCourse(@PathVariable UUID id){
        Set<TeacherDTO> teachers = service.showTeachersOfCourse(id);
        return ResponseEntity.ok(teachers);
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo curso",
            description = "Função responsável por criar um novo curso a partir de um titulo e uma descrição")
    @ApiResponses({
            @ApiResponse()
    })
    public CourseResponse createNewCourse(@Valid @RequestBody CourseRequestPayload requestPayload){
        return service.createNewCourse(requestPayload);
    }

    @PutMapping("/description/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Alterar descrição",
            description = "Função responsável por alterar a descrição de um curso")
    @ApiResponses({
            @ApiResponse()
    })
    public ResponseEntity<String> alterDescription(@PathVariable UUID id, @Valid @RequestBody DescriptionUpdateRequest descriptionUpdateRequest){
        service.alterDescription(id, descriptionUpdateRequest.description());
        return ResponseEntity.ok("Descrição atualizada com sucesso");
    }

    @PutMapping("/{courseId}/teachers")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Adicionar professor",
            description = "Função responsável por adicionar um professor a um curso")
    @ApiResponses({
            @ApiResponse()
    })
    public ResponseEntity<String> addTeacher(@RequestBody UUID teacherId, @PathVariable UUID courseId){
        service.addTeacher(teacherId, courseId);
        return ResponseEntity.ok("Professor adicionado");
    }

    @DeleteMapping("/{courseId}/teachers")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Remover professor",
            description = "Função responsável por remover um professor de um curso")
    @ApiResponses({
            @ApiResponse()
    })
    public ResponseEntity<String> removeTeacher(@RequestBody UUID teacherId, @PathVariable UUID courseId){
        service.removeTeacher(teacherId, courseId);
        return ResponseEntity.ok("Professor removido");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER'")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Deletar curso",
            description = "Funçã responsável por deletar um curso a partir do seu id")
    @ApiResponses({
            @ApiResponse()
    })
    public ResponseEntity<String> deleteCourse(@PathVariable UUID id){
        service.deleteCourse(id);
        return ResponseEntity.ok("Curso apagado.");
    }
}
