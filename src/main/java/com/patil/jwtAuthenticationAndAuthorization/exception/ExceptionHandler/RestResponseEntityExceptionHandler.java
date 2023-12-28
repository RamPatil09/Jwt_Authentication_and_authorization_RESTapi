package com.patil.jwtAuthenticationAndAuthorization.exception.ExceptionHandler;

import com.patil.jwtAuthenticationAndAuthorization.exception.UserNotFoundException;
import com.patil.jwtAuthenticationAndAuthorization.exception.response.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionMessage userNotFoundException(UserNotFoundException userNotFoundException) {
        ExceptionMessage message = new ExceptionMessage(HttpStatus.NOT_FOUND, userNotFoundException.getMessage());
        return message;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage genericExceptionHandler(Exception exception) {
        ExceptionMessage message = new ExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return message;
    }
}
