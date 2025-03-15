package com.example.javaAdvancedExamen.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {//done! EXCEPTIONS VERVANGEN WAAR MOGELIJK + CUSTOM TOEVOEGEN

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }
    @ExceptionHandler(ReservationOverlappingException.class)
    public ResponseEntity<String> handeReservationOverlapping(ReservationOverlappingException e) {
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointer(NullPointerException e) {
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }
    @ExceptionHandler(IllegalReservationException.class)
    public ResponseEntity<String> handelIllegalReservation(IllegalReservationException e) {
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> handleAlreadyExists(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<String> handleNotFoundEntity(NotFoundEntityException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRunTime(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
    //zie website java/spring boot globalexceptionhandler voor info! runtime exception
}
