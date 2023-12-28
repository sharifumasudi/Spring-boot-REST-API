package com.example.simplespringapp.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.simplespringapp.repositories.UserRepository;
import com.google.protobuf.Option;

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

    public List<Users> getUser(String username, String password) {
        Optional<Users> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (passwordEncoder.matches(password, user.getPassword())) {
                return Collections.singletonList(user);
            }
        }

        return Collections.emptyList();
    }

    public ResponseEntity<String> deleteUser(String username) {
        Optional<Users> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            Users userToDelete = userOptional.get();

            userRepository.delete(userToDelete);
            return ResponseEntity.ok("Deleted successfully");
        } else {
            return ResponseEntity.ok("User not found");
        }
    }

    public ResponseEntity<String> creaseUser(String username, String password) {
        try {
            if (userRepository.existsByUsername(username)) {
                return ResponseEntity.badRequest().body("Username already exists");
            } else if (password == null || password.isEmpty()) {
                return ResponseEntity.badRequest().body("Password can not be empty!");
            } else {
                Users user = new Users(
                        null,
                        username,
                        null,
                        null);
                user.setPassword(password);

                userRepository.save(user);

                return ResponseEntity.ok("User created successfully");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user");
        }
    }

    public ResponseEntity<String> updateUser(String username, String password){

       try{
         Users existingUser = userRepository.findByUsername(username).orElse(null);
        if(existingUser == null){
            return ResponseEntity.badRequest().body("User not found");
        }else{
            existingUser.setUsername(username);
            existingUser.setPassword(password);

            userRepository.save(existingUser);

            return ResponseEntity.ok("User updated successfully");
        }
       }catch(Exception e){
        return ResponseEntity.status(500).body("Failed to update user");
       }
    }
}
