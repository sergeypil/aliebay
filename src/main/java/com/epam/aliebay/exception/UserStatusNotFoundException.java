package com.epam.aliebay.exception;

public class UserStatusNotFoundException extends ResourceNotFoundException {

    public UserStatusNotFoundException(String message) {
        super(message);
    }
}
