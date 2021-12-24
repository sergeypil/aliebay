package com.epam.aliebay.exception;

public class CategoryNotFoundException extends ResourceNotFoundException {

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
