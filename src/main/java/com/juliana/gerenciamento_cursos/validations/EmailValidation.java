package com.juliana.gerenciamento_cursos.validations;

import com.juliana.gerenciamento_cursos.exceptions.InvalidEmailException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static String isValidEmail(String email) throws InvalidEmailException {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()) {
            return email;
        } else {
            throw new InvalidEmailException("O e-mail" + email + " é inválido");
        }
    }
}
