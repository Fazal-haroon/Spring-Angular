package com.example.springrestapi.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

public class GlobalErrorHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerError(HttpServletRequest request, Exception exception) {
        CustomError error = new CustomError(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    }
}
