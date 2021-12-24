package com.epam.aliebay.exception;

public class OrderStatusNotFoundException extends ResourceNotFoundException {

    public OrderStatusNotFoundException(String message) {
        super(message);
    }
}
