package com.github.jabadurai.tinylink.controllers;

import com.github.jabadurai.tinylink.entities.Url;
import com.github.jabadurai.tinylink.entities.User;
import com.github.jabadurai.tinylink.repositories.UrlRepository;
import com.github.jabadurai.tinylink.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserRepository userRepository;

    @Autowired
    public UserRestController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
        try {

            if(user.getId() != null){
                Optional<User> userFromDb = userRepository.findById(user.getId());
                if(userFromDb.isPresent()){
                    User updatedUser = userFromDb.get();
                    updatedUser.setUsername(user.getUsername());
                    updatedUser.setEmail(user.getEmail());
                    updatedUser.setRole(user.getRole());

                    user = updatedUser;
                }
            }

            System.out.println(user);
            userRepository.save(user);

            // Return a successful response
            Map<String, String> response = new HashMap<>();
            response.put("message", "User saved successfully!");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Handle exceptions and return an error response
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = false) Integer id) {
        try {
            userRepository.deleteById(id);

            // Return a successful response
            Map<String, String> response = new HashMap<>();
            response.put("message", "User removed successfully!");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Handle exceptions and return an error response
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/activate/{id}")
    @Transactional
    public ResponseEntity<Object> activate(@PathVariable(required = false) Integer id) {
        try {
            userRepository.activateUser(id);

            // Return a successful response
            Map<String, String> response = new HashMap<>();
            response.put("message", "User activated successfully!");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Handle exceptions and return an error response
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/deactivate/{id}")
    @Transactional
    public ResponseEntity<Object> deactivate(@PathVariable(required = false) Integer id) {
        try {
            userRepository.deactivateUser(id);

            // Return a successful response
            Map<String, String> response = new HashMap<>();
            response.put("message", "User deactivated successfully!");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Handle exceptions and return an error response
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
