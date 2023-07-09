package com.github.jabadurai.tinylink.controllers;

import com.github.jabadurai.tinylink.entities.Url;
import com.github.jabadurai.tinylink.repositories.UrlRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
public class UrlRestController {

    private final UrlRepository urlRepository;

    @Autowired
    public UrlRestController(UrlRepository urlRepository){
        this.urlRepository = urlRepository;
    }

    @PostMapping
    public ResponseEntity<Object> saveUrl(@Valid @RequestBody Url urlDto) {
        System.out.println(urlDto);
        try {
            // TODO: Save or update the URL in the database here
            // For example, you can use a service class to handle database operations
            urlRepository.save(urlDto);

            // Return a successful response
            Map<String, String> response = new HashMap<>();
            response.put("message", "URL saved successfully!");
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
            urlRepository.deleteById(id);

            // Return a successful response
            Map<String, String> response = new HashMap<>();
            response.put("message", "URL removed successfully!");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Handle exceptions and return an error response
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
