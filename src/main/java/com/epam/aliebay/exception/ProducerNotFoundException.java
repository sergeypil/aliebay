package com.epam.aliebay.exception;

public class ProducerNotFoundException extends ResourceNotFoundException {

    public ProducerNotFoundException(String message) {
        super(message);
    }
}
