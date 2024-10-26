package com.juliana.gerenciamento_cursos.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {
  public EmailAlreadyInUseException(String message) {
    super(message);
  }
}
