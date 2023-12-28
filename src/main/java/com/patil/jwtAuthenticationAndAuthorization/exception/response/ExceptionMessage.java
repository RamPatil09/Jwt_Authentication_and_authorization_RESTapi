package com.patil.jwtAuthenticationAndAuthorization.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionMessage {

    private HttpStatus httpStatus;
    private String message;
}
