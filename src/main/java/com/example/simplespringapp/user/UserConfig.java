package com.example.simplespringapp.user;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.simplespringapp.repositories.UserRepository;

@Configuration
public class UserConfig {
    
    // @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository){
        return args ->{
            Users user1 = new Users(
                 null, "abdulraufsharifu@gmail.com",
                null, null
            );
            Users user2 = new Users(
                 null, "hadija@gmail.com",
                null, null
            );

            user1.setPassword("12345");            
            user2.setPassword("12345");


            userRepository.saveAll(Arrays.asList(user1, user2));
        };
    }
}
