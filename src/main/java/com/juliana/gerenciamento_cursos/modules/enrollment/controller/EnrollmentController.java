package com.juliana.gerenciamento_cursos.modules.enrollment.controller;

import com.juliana.gerenciamento_cursos.modules.enrollment.dto.EnrollmentDTO;
import com.juliana.gerenciamento_cursos.modules.enrollment.dto.EnrollmentRequestPayload;
import com.juliana.gerenciamento_cursos.modules.enrollment.dto.EnrollmentResponse;
import com.juliana.gerenciamento_cursos.modules.enrollment.service.EnrollmentService;
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
import java.util.UUID;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    @Autowired
    EnrollmentService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Mostrar todas as inscrições",
            description = "Função responsável por retornar uma lista de dtos de enrollments com todos os existentes")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de EnrollmentDTO",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EnrollmentDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mensagem de erro: 'Nenhuma inscrição encontrada'"
            )
    })
    public ResponseEntity<List<EnrollmentDTO>> showAllEnrollments(){
        List<EnrollmentDTO> enrollments = service.showAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Mostrar inscrições de um curso",
            description = "Função responsável por mostrar as inscrições de um cruso a partir do seu id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de EnrollmentDTO",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EnrollmentDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mensagem de erro : 'Nenhuma inscrição encontrada para esse curso'"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mensagem de erro: 'Curso não encontrado'"
            )
    })
    public ResponseEntity<List<EnrollmentDTO>> showCourseEnrollments(@PathVariable UUID courseId){
        List<EnrollmentDTO> enrollments =  service.showCourseEnrollments(courseId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/course/{courseId}/active")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Mostrar inscrições ativas de um curso",
            description = "Função responsável por mostrar somente as inscrições atualmente ativas de um curso")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de EnrollmentDTO",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EnrollmentDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mensagem de erro : 'Nenhuma inscrição encontrada para esse curso'"
            )
    })
    public ResponseEntity<List<EnrollmentDTO>> showCourseActiveEnrollments(@PathVariable UUID courseId){
        List<EnrollmentDTO> enrollments =  service.showCourseActiveEnrollments(courseId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/student/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Mostrar inscrições de um aluno",
            description = "Função responsável por mostrar todo o histórico de inscriçẽs de um aluno")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de EnrollmentDTO",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EnrollmentDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mensagem de erro : 'Nenhuma inscrição encontrada para esse aluno'"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mensagem de erro : 'Estudante não encontrado'"
            )
    })
    public ResponseEntity<List<EnrollmentDTO>> showStudentEnrollments(@PathVariable UUID studentId){
        List<EnrollmentDTO> enrollments =  service.showStudentEnrollments(studentId);
        return ResponseEntity.ok(enrollments);
    }

    @PostMapping("/subscribe")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Inscrever estudante em um curso",
            description = "Função responsável por inscrever um estudante em um cruso a partir do seu id e do id do curso")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Id da inscrição",
                    content = @Content(schema = @Schema(implementation = EnrollmentResponse.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Mensagem de erro: 'Aluno já inscrito neste curso'"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Mensagem de erro: 'Aluno não encontrado'"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Mensagem de erro: 'Curso não encontrado'"
            )
    })
    public EnrollmentResponse enrollStudentInCourse(@Valid @RequestBody EnrollmentRequestPayload requestPayload){
        return service.enrollStudentInCourse(requestPayload);
    }

    @PutMapping("/unsubscribe")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Desativar inscrição de um estudante",
            description = "Função responsável por tornar a inscrição de um estudante em um curso inativa")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Mensagem de sucesso: 'Esta inscrição foi inativada'",
                    content = @Content(schema = @Schema(implementation = ResponseEntity.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Mensagem de erro: 'Inscrição não encontrada'"
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Mensagem de erro: 'A inscrição já está inativa'"
            )
    })
    public ResponseEntity<String> unsubscribeStudentOfCourse(@Valid @RequestBody EnrollmentRequestPayload requestPayload){
       service.unsubscribeStudentOfCourse(requestPayload);
       return ResponseEntity.ok("Esta inscrição foi inativada");
    }

}
