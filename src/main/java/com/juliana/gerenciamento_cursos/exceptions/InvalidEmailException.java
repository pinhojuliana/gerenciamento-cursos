package com.juliana.gerenciamento_cursos.exceptions;

public class InvalidEmailException extends Exception {
    public InvalidEmailException(String message) {
      super(message);
    }
}
