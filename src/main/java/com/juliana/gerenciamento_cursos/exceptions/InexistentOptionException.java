package com.juliana.gerenciamento_cursos.exceptions;

public class InexistentOptionException extends RuntimeException {
    public InexistentOptionException(String message) {
        super(message);
    }
}
