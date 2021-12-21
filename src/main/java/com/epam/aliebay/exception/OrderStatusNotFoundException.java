package com.epam.aliebay.exception;

public class OrderStatusNotFoundException extends RuntimeException {

    public OrderStatusNotFoundException(String message) {
        super(message);
    }
}
