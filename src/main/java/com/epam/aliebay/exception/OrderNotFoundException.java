package com.epam.aliebay.exception;

public class OrderNotFoundException extends ResourceNotFoundException {

    public OrderNotFoundException(String message) {
        super(message);
    }
}
