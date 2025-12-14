package com.avaliacaopratica.api.exceptions;

public class IntegrityViolation extends RuntimeException {

    public IntegrityViolation(String message) {
        super(message);
    }

}