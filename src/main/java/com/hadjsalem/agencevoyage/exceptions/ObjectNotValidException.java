package com.hadjsalem.agencevoyage.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ObjectNotValidException extends RuntimeException{

private  final Set<String> errorMessages;

}
