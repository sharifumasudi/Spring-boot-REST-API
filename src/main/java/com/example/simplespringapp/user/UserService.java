package com.example.simplespringapp.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

import com.example.simplespringapp.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> getUsers() {
        return userRepository.findAll();
    }

    public List<Users> getUser(Integer Id) {
        Optional<Users> userOptional = userRepository.findById(Id);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            return Collections.singletonList(user);
            // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // if (passwordEncoder.matches(password, user.getPassword())) {
            // }
        }

        return Collections.emptyList();
    }

    public ResponseEntity<String> deleteUser(String username) {
        if (username != null || !username.isEmpty() || username.matches("^[a-zA-Z0-9_]+$")) {
            Optional<Users> userOptional = userRepository.findByUsername(username);

            if (userOptional.isPresent()) {
                Users userToDelete = userOptional.get();

                userRepository.delete(userToDelete);
                return ResponseEntity.ok("Deleted successfully");
            } else {
                return ResponseEntity.ok("User not found");
            }
        } else {
            return ResponseEntity.ok("Provided username is incorrect");

        }
    }

    public ResponseEntity<String> creaseUser(String username, String password) {
        try {
            if (userRepository.existsByUsername(username)) {
                return ResponseEntity.badRequest().body("Username already exists");
            } else if (password == null || password.isEmpty()) {
                return ResponseEntity.badRequest().body("Password can not be empty!");
            } else {
                LocalDateTime now = LocalDateTime.now();

                Users user = new Users(
                        null,
                        username,
                        now,
                        null);
                user.setPassword(password);

                userRepository.save(user);

                return ResponseEntity.ok("User created successfully");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user");
        }
    }

    public ResponseEntity<String> updateUser(String username, String password) {

        try {
            Users existingUser = userRepository.findByUsername(username).orElse(null);
            if (existingUser == null) {
                return ResponseEntity.badRequest().body("User not found");
            } else {
                existingUser.setUsername(username);
                existingUser.setPassword(password);

                userRepository.save(existingUser);

                return ResponseEntity.ok("User updated successfully");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update user");
        }
    }
}
