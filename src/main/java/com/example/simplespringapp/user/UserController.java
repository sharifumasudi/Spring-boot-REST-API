package com.example.simplespringapp.user;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity<Iterable<Users>> getUsers(@RequestParam(value = "page") int page,
            @RequestParam(value = "limit") int limit) {
        return ResponseEntity.ok(studentService.getUsers(page, limit));
    }

    @GetMapping(path = "/{userUid}")
    public List<Users> getUser(@PathVariable Integer userUid) {

        return studentService.getUser(userUid);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUser(String username) {
        return studentService.deleteUser(username);
    }

    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
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
