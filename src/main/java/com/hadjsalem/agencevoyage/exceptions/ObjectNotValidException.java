package com.hadjsalem.agencevoyage.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data


public class ObjectNotValidException extends RuntimeException{

private  final Set<String> errorMessages;

    public ObjectNotValidException(Set<String> errorMessages) {
        super(String.join(", ", errorMessages)); // Concatène les messages d'erreur
        this.errorMessages = errorMessages;
    }

    public Set<String> getErrorMessages() {
        return errorMessages;
    }

}
