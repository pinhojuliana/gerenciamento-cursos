package com.juliana.gerenciamento_cursos.application.exceptions;

public class TitleAlreadyInUseException extends RuntimeException {
    public TitleAlreadyInUseException(String message) {
        super(message);
    }
}
