package com.juliana.gerenciamento_cursos.exceptions;

public class UsernameAlreadyInUseException extends RuntimeException {
    public UsernameAlreadyInUseException(String message) {
        super(message);
    }
}
