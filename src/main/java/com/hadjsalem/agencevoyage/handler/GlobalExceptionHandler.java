package com.hadjsalem.agencevoyage.handler;

import com.hadjsalem.agencevoyage.exceptions.DuplicateEntryException;
import com.hadjsalem.agencevoyage.exceptions.ObjectNotValidException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleException(IllegalStateException  exception){
        return   ResponseEntity
                .badRequest()
                .body("Illegal State" + exception.getMessage());
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleException(EntityNotFoundException exception){
        return   ResponseEntity
                .badRequest()
                .body("Entity Not Found: " + exception.getMessage());
    }


    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<?> handleException(ObjectNotValidException exception){
        return   ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<?> handleException(DuplicateEntryException exception){
        return   ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }


}
