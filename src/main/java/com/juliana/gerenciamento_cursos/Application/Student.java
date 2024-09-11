package com.juliana.gerenciamento_cursos.Application;

import com.juliana.gerenciamento_cursos.entity.educational_platform.EducationalPlatform;
import com.juliana.gerenciamento_cursos.exceptions.InvalidEmailException;
import com.juliana.gerenciamento_cursos.validations.DateValidation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DateTimeException;
import java.util.Scanner;

@Data
@AllArgsConstructor
public class Student {
    private EducationalPlatform educationalPlatform;
    private Scanner scanner;

}
