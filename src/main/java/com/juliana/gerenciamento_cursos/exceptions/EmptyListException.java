package com.juliana.gerenciamento_cursos.exceptions;

public class EmptyListException extends RuntimeException {
  public EmptyListException(String message) {
    super(message);
  }
}
