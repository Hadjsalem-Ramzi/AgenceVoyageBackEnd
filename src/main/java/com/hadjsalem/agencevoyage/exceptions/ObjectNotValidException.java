package com.hadjsalem.agencevoyage.exceptions;

import lombok.*;

import java.util.Set;


@Getter
@ToString
@Setter
public class ObjectNotValidException extends RuntimeException{

private  final Set<String> errorMessages;

    public ObjectNotValidException(Set<String> errorMessages) {
        super(String.join(", ", errorMessages)); // Concatène les messages d'erreur
        this.errorMessages = errorMessages;
    }

}
