package com.juliana.gerenciamento_cursos.entity.user;

public class UsernameAlreadyInUseException extends RuntimeException {
  public UsernameAlreadyInUseException(String message) {
    super(message);
  }
}
