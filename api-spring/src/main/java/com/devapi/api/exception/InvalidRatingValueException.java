package com.devapi.api.exception;

public class InvalidRatingValueException extends Exception {
    public InvalidRatingValueException() {
        super("Valor de avaliação inválido. A avaliação não pode ser negativa.");
    }
}
