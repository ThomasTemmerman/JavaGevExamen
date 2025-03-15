package com.example.javaAdvancedExamen.exceptions;

public class ReservationOverlappingException extends RuntimeException{
    public ReservationOverlappingException(String message) {
        super(message);
    }
}
