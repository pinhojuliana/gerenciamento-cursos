package com.juliana.gerenciamento_cursos.application.exceptions;

public class UnderageException extends RuntimeException {
    public UnderageException(String message) {
        super(message);
    }
}
