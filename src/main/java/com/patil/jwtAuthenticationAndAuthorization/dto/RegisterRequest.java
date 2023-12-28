package com.patil.jwtAuthenticationAndAuthorization.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String phonenumber;
    private String password;
    private String role;
}
