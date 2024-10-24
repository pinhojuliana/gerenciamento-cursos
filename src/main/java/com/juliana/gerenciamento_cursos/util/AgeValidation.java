package com.juliana.gerenciamento_cursos.util;

import com.juliana.gerenciamento_cursos.exceptions.UnderageException;

import java.time.LocalDate;
import java.time.Period;

public class AgeValidation {
    public static boolean validateAge(LocalDate dateOfBirth) throws UnderageException{
        LocalDate currentlyDay = LocalDate.now();
        if (Period.between(dateOfBirth, currentlyDay).getYears() >= 18) {
            return true;
        }
        throw new UnderageException("Menores de 18 anos não podem se inscrever na plataforma");
    }
}
