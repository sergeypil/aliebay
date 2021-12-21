package com.epam.aliebay.exception;

public class ProducerNotFoundException extends RuntimeException {

    public ProducerNotFoundException(String message) {
        super(message);
    }
}
