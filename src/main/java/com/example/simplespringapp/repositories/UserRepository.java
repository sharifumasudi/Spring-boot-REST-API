package com.example.simplespringapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.simplespringapp.user.Users;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>{

    Optional<Users> findByUsername(String username);    
    
    Optional<Users> findById(Integer id);


    boolean existsByUsername(String username);
}
