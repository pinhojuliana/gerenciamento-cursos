package com.juliana.gerenciamento_cursos.modules.lesson.controller;

import com.juliana.gerenciamento_cursos.modules.lesson.dto.LessonDTO;
import com.juliana.gerenciamento_cursos.modules.lesson.dto.LessonRequestPayload;
import com.juliana.gerenciamento_cursos.modules.lesson.dto.LessonResponse;
import com.juliana.gerenciamento_cursos.modules.lesson.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lessons")
@Tag(name = "Lesson", description = "Informaçõe sosbre as aulas")
public class LessonController {
    @Autowired
    LessonService service;

    @GetMapping("/units/{unitId}")
    @PreAuthorize("has role('MANAGER', 'STUDENT', 'TEACHER')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Aulas de um módulo",
            description = "Essa função é responsável por buscar as aulas de um modulo")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de aulas de um modulo",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = LessonDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Modulo não encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhuma aula encontrada para esse módulo"
            )
    })
    public ResponseEntity<List<LessonDTO>> showLessonsOfModule(@PathVariable UUID unitId){
        List<LessonDTO> classes = service.showLessonsOfModule(unitId);
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/search")
    @PreAuthorize("has role('MANAGER', 'STUDENT', 'TEACHER')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Pesquisar aula pelo título",
            description = "Essa função é responsável por buscar as aulas pelo título")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de aulas com um título",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = LessonDTO.class)))
            ),
         @ApiResponse(
                    responseCode = "404",
                    description = "Nenhuma aula encontrada"
            )
    })
    public ResponseEntity<List<LessonDTO>> findLessonsByTitle(@RequestParam @NotEmpty String lessonTitle){
        List<LessonDTO> lessons = service.findLessonsByTitle(lessonTitle);
        return ResponseEntity.ok(lessons);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar nova aula",
            description = "Essa função é responsável por criar uma aula")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Id da aula",
                    content = @Content(schema = @Schema(implementation = LessonResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Módulo não encontrado"
            )
    })
    public LessonResponse createNewLesson(@Valid @RequestBody LessonRequestPayload requestPayload){
        return service.createNewLesson(requestPayload);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Deletar aula",
            description = "Essa função é responsável por deletar aulas pelo id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Mensagem de sucesso",
                    content = @Content(schema = @Schema(implementation = ResponseEntity.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Id não encontrado"
            )
    })
    public ResponseEntity<String> deleteLesson(@PathVariable UUID id){
        service.deleteLesson(id);
        return ResponseEntity.ok("Aula deletada com sucesso");
    }
}
