package com.juliana.gerenciamento_cursos.application.exceptions;

public class EmptyListException extends RuntimeException {
    public EmptyListException(String message) {
        super(message);
    }
}
