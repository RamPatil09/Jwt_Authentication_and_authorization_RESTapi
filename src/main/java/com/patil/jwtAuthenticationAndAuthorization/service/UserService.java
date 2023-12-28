package com.patil.jwtAuthenticationAndAuthorization.service;


import com.patil.jwtAuthenticationAndAuthorization.dto.RegisterRequest;
import com.patil.jwtAuthenticationAndAuthorization.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<String> save(RegisterRequest request);

    User findByEmail(String email);
    User findById(Long id);

    ResponseEntity<String> deleteById(Long id);

    ResponseEntity<String> deleteByEmail(String email);

    List<User> findAll();
}
