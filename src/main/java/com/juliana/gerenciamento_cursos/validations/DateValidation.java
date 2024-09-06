package com.juliana.gerenciamento_cursos.validations;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidation {
    public static LocalDate formatDate(String date) throws DateTimeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }

    public static String formatDate(LocalDate date) throws DateTimeException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatter.format(date);
    }
}
