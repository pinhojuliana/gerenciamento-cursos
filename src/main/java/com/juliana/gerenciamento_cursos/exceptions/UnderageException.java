package com.juliana.gerenciamento_cursos.exceptions;

public class UnderageException extends RuntimeException {
    public UnderageException(String message) {
        super(message);
    }
}
