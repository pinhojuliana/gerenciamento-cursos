package com.juliana.gerenciamento_cursos.util;

import com.juliana.gerenciamento_cursos.exceptions.InvalidAgeException;

import java.time.LocalDate;
import java.time.Period;

public class AgeValidation {
    public static LocalDate validateAge(LocalDate dateOfBirth) throws InvalidAgeException {
        LocalDate currentlyDay = LocalDate.now();
        if (Period.between(dateOfBirth, currentlyDay).getYears() >= 18) {
            return dateOfBirth;
        }
        throw new InvalidAgeException("Você precisa ter no mínimo 18 anos para se inscrever na plataforma");
    }
}
