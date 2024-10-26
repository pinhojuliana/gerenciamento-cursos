package com.juliana.gerenciamento_cursos.exceptions;

public class TitleAlreadyInUseException extends RuntimeException {
  public TitleAlreadyInUseException(String message) {
    super(message);
  }
}
