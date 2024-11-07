package com.juliana.gerenciamento_cursos.exceptions;

public class SkillAlreadyExistsException extends RuntimeException {
  public SkillAlreadyExistsException(String message) {
    super(message);
  }
}
