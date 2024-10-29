package com.juliana.gerenciamento_cursos.application.exceptions;

public class InexistentOptionException extends RuntimeException {
    public InexistentOptionException(String message) {
        super(message);
    }
}
