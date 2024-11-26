package com.oocl.springbootemployee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(EmployeeNotFundException exception) {
        return new ErrorResponse("EmployeeNotFoundException");
    }

    @ExceptionHandler(EmployeeCreateInvalidException.class)
    public ErrorResponse handle(EmployeeCreateInvalidException exception) {
        return new ErrorResponse("EmployeeNotFoundException");
    }

    @ExceptionHandler(EmployeeInactivityException.class)
    public ErrorResponse handle(EmployeeInactivityException exception) {
        return new ErrorResponse("EmployeeNotFoundException");
    }
}
