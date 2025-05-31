package com.example.javaAdvancedExamen.exceptions;


public class NotFoundEntityException extends RuntimeException{//unchecked
    public NotFoundEntityException(String message) {
        super(message);
    }
}
