package com.alatoo.Image.API.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(NotFoundException ex, WebRequest request) {
        String errorDetails = ex.getMessage();
        CustomErrorResponse error = new CustomErrorResponse(HttpStatus.NOT_FOUND, errorDetails);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}