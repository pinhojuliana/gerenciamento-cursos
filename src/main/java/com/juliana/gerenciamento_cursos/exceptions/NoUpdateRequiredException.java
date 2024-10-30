package com.juliana.gerenciamento_cursos.exceptions;

public class NoUpdateRequiredException extends RuntimeException {
  public NoUpdateRequiredException(String message) {
    super(message);
  }
}
