package com.juliana.gerenciamento_cursos.DTOs;

import com.juliana.gerenciamento_cursos.domain.EducationalLevel;

public record CreateStudentRequest(ClientRequestPayload requestPayload,
                                   String description,
                                   EducationalLevel educationalLevel)
{}
