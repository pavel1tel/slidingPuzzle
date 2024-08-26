package com.testing.slidingpuzzle.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<?> gameNotFoundExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProhibitedMoveException.class)
    public ResponseEntity<?> prohibitedMoveException(Exception exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}
