package com.github.jabadurai.tinylink.service;

import com.github.jabadurai.tinylink.dto.ChangePasswordRequest;
import com.github.jabadurai.tinylink.entities.CustomUserDetails;
import com.github.jabadurai.tinylink.entities.User;
import com.github.jabadurai.tinylink.repositories.UserRepository;
import com.github.jabadurai.tinylink.validators.UserValidator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

@Service
public class UserService {

    List<String> updatableFields = List.of("fullName", "username", "email", "role");

    public final UserValidator userValidator;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    public Optional<Integer> currentLoggedInUserId(){
        Optional<CustomUserDetails> user = currentLoggedInUser();
        return user.map(customUserDetails -> customUserDetails.getUser().getId());
    }

    public Optional<CustomUserDetails> currentLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return Optional.of(userDetails);
        }
        return Optional.empty();
    }

    public Page<User> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.userRepository.findAll(pageable);
    }

    @Transactional
    public ResponseEntity<Object> saveUserNew(User user, BindingResult bindingResult){

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

            userValidator.validate(user, bindingResult);

            if(bindingResult.hasErrors()){
                return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
            } else {
                saveUserToDb(user);
                // Return a successful response
                Map<String, String> response = new HashMap<>();
                response.put("message", "User saved successfully!");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        } catch (DataIntegrityViolationException ex) {
            // This exception is thrown when unique constraint is violated
            return new ResponseEntity<>(Collections.singletonList(new FieldError("username","username","Username already exists, Please choose a different one")), HttpStatus.CONFLICT);
        } catch (Exception ex) {
            // Handle other exceptions
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(Collections.singletonList(new FieldError("username","username","Unable to process your request")),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional
    private void saveUserToDb(User user) {
        userRepository.save(user);
    }

    @Transactional
    public ResponseEntity<Object> deleteUser(Integer id){
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

    @Transactional
    public ResponseEntity<Object> activateUser(Integer id) {
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

    @Transactional
    public ResponseEntity<Object> deactivateUser(Integer id) {
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

    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        Optional<Integer> currentUserId = currentLoggedInUserId();
        currentUserId.ifPresent(integer -> userRepository.changePassword(integer, changePasswordRequest.getNewPassword()));
    }
}
