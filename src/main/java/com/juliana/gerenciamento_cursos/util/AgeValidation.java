package com.juliana.gerenciamento_cursos.util;

import com.juliana.gerenciamento_cursos.exceptions.UnderageException;

import java.time.LocalDate;
import java.time.Period;

public class AgeValidation {
    public static LocalDate validateAge(LocalDate dateOfBirth) throws UnderageException{
        LocalDate currentlyDay = LocalDate.now();
        if (Period.between(dateOfBirth, currentlyDay).getYears() >= 18) {
            return dateOfBirth;
        }
        throw new UnderageException("Você precisa ter no mínimo 18 anos para se inscrever na plataforma");
    }
}
