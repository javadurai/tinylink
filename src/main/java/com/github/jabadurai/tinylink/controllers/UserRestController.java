package com.github.jabadurai.tinylink.controllers;

import com.github.jabadurai.tinylink.entities.User;
import com.github.jabadurai.tinylink.repositories.UserRepository;
import com.github.jabadurai.tinylink.service.UserDetailsServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserRepository userRepository;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public UserRestController(UserRepository userRepository, UserDetailsServiceImpl userDetailsServiceImpl){
        this.userRepository = userRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<Object> saveUser(@Valid @RequestBody User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // handle errors here, you might want to return them in the response body
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            userDetailsServiceImpl.saveUser(user);
            // Return a successful response
            Map<String, String> response = new HashMap<>();
            response.put("message", "User saved successfully!");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DataIntegrityViolationException ex) {
            // This exception is thrown when unique constraint is violated
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        } catch (Exception ex) {
            // Handle other exceptions
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
