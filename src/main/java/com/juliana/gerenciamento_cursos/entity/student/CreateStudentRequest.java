package com.juliana.gerenciamento_cursos.entity.student;

import com.juliana.gerenciamento_cursos.entity.client.ClientRequestPayload;

public record CreateStudentRequest(ClientRequestPayload requestPayload,
                                   String description,
                                   EducationalLevel educationalLevel)
{}
