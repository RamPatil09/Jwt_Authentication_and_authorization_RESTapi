package com.patil.jwtAuthenticationAndAuthorization.repository;

import com.patil.jwtAuthenticationAndAuthorization.model.ERole;
import com.patil.jwtAuthenticationAndAuthorization.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String role);
}
