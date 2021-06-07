package com.desafio.encurtadorurl.exception;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ExpirationDateException.class)
    protected ResponseEntity<ExceptionResponse> handlerExpirationDate(ExpirationDateException exception) {
        return  ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(ExceptionResponse.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ExceptionResponse> handlerNotFound(NotFoundException exception) {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionResponse.builder().message(exception.getMessage()).build());
    }
}
