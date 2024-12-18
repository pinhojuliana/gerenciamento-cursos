package com.juliana.gerenciamento_cursos.modules.client.controller;

import com.juliana.gerenciamento_cursos.modules.client.dto.ManagerRequestPayload;
import com.juliana.gerenciamento_cursos.modules.client.dto.ManagerResponse;
import com.juliana.gerenciamento_cursos.modules.client.service.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private ManagerService service;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "",
            description = ""
    )
    @ApiResponses({
            @ApiResponse(),
            @ApiResponse(),
            @ApiResponse(),
            @ApiResponse()
    })
    public ResponseEntity<ManagerResponse> createManager(@RequestBody @Valid ManagerRequestPayload requestPayload){
        var result = service.createManager(requestPayload);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "",
            description = ""
    )
    @ApiResponses({
            @ApiResponse(),
            @ApiResponse()
    })
    public ResponseEntity<String> removeManager(@PathVariable UUID id){
        service.removeManager(id);
        return ResponseEntity.ok("Manager removido.");
    }
}
