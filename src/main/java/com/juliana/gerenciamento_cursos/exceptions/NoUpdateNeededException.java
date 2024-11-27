package com.juliana.gerenciamento_cursos.exceptions;

public class NoUpdateNeededException extends RuntimeException {
    public NoUpdateNeededException(String message) {
        super(message);
    }
}
