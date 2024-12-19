package com.juliana.gerenciamento_cursos.modules.unit.controller;

import com.juliana.gerenciamento_cursos.modules.unit.dto.UnitDTO;
import com.juliana.gerenciamento_cursos.modules.unit.dto.UnitRequestPayload;
import com.juliana.gerenciamento_cursos.modules.unit.dto.UnitResponse;
import com.juliana.gerenciamento_cursos.modules.unit.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/units")
public class UnitController {
    @Autowired
    UnitService service;

    @GetMapping("/course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Módulos de um curso",
            description = "Essa função é responsável por buscar os módulos de um curso")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de módulos de um curso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UnitDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Curso não encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhuma aula encontrada para esse curso"
            )
    })
    public ResponseEntity<List<UnitDTO>> showUnitsOfCourse(@PathVariable UUID courseId){
        List<UnitDTO> modules = service.findUnitsByCourse(courseId);
        return ResponseEntity.ok(modules);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Aulas de um módulo",
            description = "Essa função é responsável por buscar os módulos pelo título")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de módulos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UnitDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhum módulo encontrado"
            ),
    })
    public ResponseEntity<List<UnitDTO>> findUnits(@RequestParam @NotEmpty String moduleTitle){
        List<UnitDTO> modules = service.findUnits(moduleTitle);
        return ResponseEntity.ok(modules);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Criar novo módulo",
            description = "Essa função é responsável por criar um módulo")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Id do módulo",
                    content = @Content(schema = @Schema(implementation = ResponseEntity.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Id não encontrado"
            )
    })
    public UnitResponse createNewUnit(@Valid @RequestBody UnitRequestPayload requestPayload){
        return service.createNewUnit(requestPayload);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Deletar módulo",
            description = "Essa função é responsável por deletar modulos pelo id")
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
    public ResponseEntity<String> deleteUnit(@PathVariable UUID id){
        service.deleteUnit(id);
        return ResponseEntity.ok("Modulo deletado com sucesso");
    }
}
