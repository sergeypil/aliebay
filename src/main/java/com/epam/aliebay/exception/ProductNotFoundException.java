package com.epam.aliebay.exception;

public class ProductNotFoundException extends ResourceNotFoundException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
