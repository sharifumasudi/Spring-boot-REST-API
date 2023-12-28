package com.example.simplespringapp.user;

import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private LocalDate created_at;

    private String password;

    public Users() {

    }

    public Users(Integer id, String username, LocalDate created_at, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.created_at = created_at;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getCreatedAt() {
        return created_at;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer Id) {
        this.id = Id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDate(LocalDate created_at) {
        this.created_at = created_at;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    @Override
    public String toString() {
        return "User {" +
                "id=" + id +
                ", Username='" + username + '\'' +
                ", created date='" + created_at + '\'' +
                '}';
    }

}
