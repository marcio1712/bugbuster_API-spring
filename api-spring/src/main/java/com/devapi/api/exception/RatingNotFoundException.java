package com.devapi.api.exception;

public class RatingNotFoundException extends Exception {

    public RatingNotFoundException() {
        super("Nao foi encontrada nenhuma avaliacao para este evento");
    }
}
