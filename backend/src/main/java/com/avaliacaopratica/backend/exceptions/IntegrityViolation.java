package com.avaliacaopratica.backend.exceptions;

public class IntegrityViolation extends RuntimeException {

    public IntegrityViolation(String message) {
        super(message);
    }

}