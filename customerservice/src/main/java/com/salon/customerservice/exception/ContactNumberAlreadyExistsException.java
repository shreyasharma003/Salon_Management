package com.salon.customerservice.exception;

public class ContactNumberAlreadyExistsException extends RuntimeException {
    public ContactNumberAlreadyExistsException(String message) {
        super(message);
    }
}
