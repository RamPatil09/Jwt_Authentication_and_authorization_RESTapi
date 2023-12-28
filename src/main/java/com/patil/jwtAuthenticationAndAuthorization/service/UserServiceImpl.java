package com.patil.jwtAuthenticationAndAuthorization.service;

import com.patil.jwtAuthenticationAndAuthorization.dto.RegisterRequest;
import com.patil.jwtAuthenticationAndAuthorization.exception.RoleNotFoundException;
import com.patil.jwtAuthenticationAndAuthorization.exception.UserNotFoundException;
import com.patil.jwtAuthenticationAndAuthorization.model.ERole;
import com.patil.jwtAuthenticationAndAuthorization.model.Role;
import com.patil.jwtAuthenticationAndAuthorization.model.User;
import com.patil.jwtAuthenticationAndAuthorization.repository.RoleRepository;
import com.patil.jwtAuthenticationAndAuthorization.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> save(RegisterRequest request) {
        try {
            if (request != null) {
                User user = new User();
                user.setFirstname(request.getFirstname());
                user.setLastname(request.getLastname());
                user.setEmail(request.getEmail());
                user.setPhonenumber(request.getPhonenumber());
                user.setPassword(passwordEncoder.encode(request.getPassword()));

                Role role = roleCheck(request.getRole());
                user.setRoles(Arrays.asList(role));
                userRepository.save(user);
                return ResponseEntity.ok("User registered successfully!");
            } else {
                return ResponseEntity.badRequest().body("Invalid Registration request");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user");
        }
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new UserNotFoundException("" + "User not found with email: " + email));
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException("" + "User not found with ID: " + id));
    }


    @Override
    public ResponseEntity<String> deleteById(Long id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setRoles(Collections.emptyList());
                userRepository.delete(user);
                return ResponseEntity.ok("User deleted successfully");
            } else {
                throw new UserNotFoundException("User not found with ID: " + id);
            }
        } catch (UserNotFoundException e) {
            throw e;  // Re-throw UserNotFoundException to let it propagate up
        } catch (Exception e) {
            logger.error("Error deleting user with ID " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }

    @Override
    public ResponseEntity<String> deleteByEmail(String email) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setRoles(Collections.emptyList());
                userRepository.delete(user);
                return ResponseEntity.ok("User deleted successfully");
            } else {
                throw new UserNotFoundException("" + "User not found with email: " + email);
            }
        } catch (UserNotFoundException e) {
            throw e;  // Re-throw UserNotFoundException to let it propagate up
        } catch (Exception e) {
            logger.error("Error deleting user with Email " + email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            logger.info("Error fetching all users", e);
            return Collections.emptyList();
        }
    }


    private Role roleCheck(String role) {
        try {
            if (role == null) {
                role = "user"; // Set a default role value, or choose another default value as needed
            }

            switch (role.toLowerCase()) {
                case "admin":
                    // Using Optional to handle the possibility of a null result
                    return roleRepository.findByName(ERole.ROLE_ADMIN.name()).orElseThrow(() -> new RoleNotFoundException("Role not found: ADMIN"));
                default:
                    // Using Optional to handle the possibility of a null result
                    return roleRepository.findByName(ERole.ROLE_USER.name()).orElseThrow(() -> new RoleNotFoundException("Role not found: USER"));
            }
        } catch (RoleNotFoundException e) {
            e.printStackTrace(); // Handle or log the exception accordingly
            return new Role(); // Or return null or another default role
        }
    }

}
