package com.devapi.api.exception;

public class RatingEmptyException extends Exception{

    public RatingEmptyException() {
        super("A lista de avaliações esta vazia");
    }
}
