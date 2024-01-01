package com.example.simplespringapp.user;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    private final UserService studentService;

    @Autowired
    public UserController(UserService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public List<Users> getUsers() {
        return studentService.getUsers();
    }

    @GetMapping(path = "/{userUid}")
    public List<Users> getUser(@PathVariable Integer userUid) {

        return studentService.getUser(userUid);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUser(String username) {
        return studentService.deleteUser(username);
    }

    @PostMapping()
    public ResponseEntity<String> storeUser(@RequestBody JsonNode requestBody) {

        String username = requestBody.get("username").asText();
        String password = requestBody.get("password").asText();

        return studentService.creaseUser(username, password);
    }

    @PutMapping()
    public ResponseEntity<String> updateUser(@RequestBody JsonNode requestBody) {

        String username = requestBody.get("username").asText();
        String password = requestBody.get("password").asText();

        return studentService.updateUser(username, password);
    }
}
