package com.juliana.gerenciamento_cursos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EmptyListException.class)
    public ResponseEntity<String> emptyListException(EmptyListException e){
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(InexistentOptionException.class)
    public ResponseEntity<String> inexistentOptionException(InexistentOptionException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public void usernameAlreadyInUse(UsernameAlreadyInUseException e){

    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public void emailAlreadyInUse(EmailAlreadyInUseException e){

    }

    @ExceptionHandler(UnderageException.class)
    public void underageException(UnderageException e){

    }

    @ExceptionHandler(InvalidPasswordException.class)
    public void InvalidPasswordException(InvalidPasswordException e){

    }

    @ExceptionHandler(TitleAlreadyInUseException.class)
    public void titleAlreadyInUse(TitleAlreadyInUseException e){

    }

}
