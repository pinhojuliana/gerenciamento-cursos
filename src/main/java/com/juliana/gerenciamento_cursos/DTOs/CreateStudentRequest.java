package com.juliana.gerenciamento_cursos.DTOs;

import com.juliana.gerenciamento_cursos.domain.client.EducationalLevel;

public record CreateStudentRequest(ClientRequestPayload requestPayload,
                                   String description,
                                   EducationalLevel educationalLevel)
{}
