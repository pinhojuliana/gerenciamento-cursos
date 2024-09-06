package com.juliana.gerenciamento_cursos.validations;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidation {
    public static LocalDate formatDate(String date) throws DateTimeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate formatedDate = LocalDate.parse(date, formatter);
        return formatedDate;
    }
}
