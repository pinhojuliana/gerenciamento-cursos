package com.juliana.gerenciamento_cursos.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EnrollmentDTO(String courseTitle,
                            String studentUsername,
                            LocalDateTime enrollmentDateTime,
                            LocalDate deadlineForCompletion,
                            int duration,
                            boolean active) {}
