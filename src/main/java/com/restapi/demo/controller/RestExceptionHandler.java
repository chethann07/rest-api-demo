package com.restapi.demo.controller;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.sql.SQLNonTransientConnectionException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    Map<String, String> message = new HashMap<>();

    @ExceptionHandler({DataAccessResourceFailureException.class,
            CannotCreateTransactionException.class,
            SQLNonTransientConnectionException.class
    })
    public ResponseEntity<Map<String, String>> handleDBException(Exception e) {
        message.put("message", "database connection failed");
        message.put("status", HttpStatus.SERVICE_UNAVAILABLE.toString());
        message.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        message.put("message", "status is invalid");
        message.put("status", HttpStatus.BAD_REQUEST.toString());
        message.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, String>> handleNullPointerException(NullPointerException e) {
        message.put("message", "some of the fields are missing");
        message.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        message.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(InputMismatchException.class)
    public ResponseEntity<Map<String, String>> handleInputMismatchException(InputMismatchException e) {
        message.put("message", e.getMessage());
        message.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        message.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        message.put("message", "dataset already exists");
        message.put("status", HttpStatus.BAD_REQUEST.toString());
        message.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        message.put("message", e.getMessage());
        return  ResponseEntity.internalServerError().body(message);
    }
}
