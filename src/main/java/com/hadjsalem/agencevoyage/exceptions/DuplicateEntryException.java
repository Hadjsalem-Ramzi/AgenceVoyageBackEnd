package com.hadjsalem.agencevoyage.exceptions;

public class DuplicateEntryException extends  RuntimeException{
    public DuplicateEntryException(String message){
        super(message);
    }
}
