package com.github.jabadurai.tinylink.controllers;

import com.github.jabadurai.tinylink.entities.User;
import com.github.jabadurai.tinylink.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<Object> saveUser(@RequestBody User user, BindingResult bindingResult) {
        return userService.saveUserNew(user, bindingResult);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Object> delete(@PathVariable(required = false) Integer id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/activate/{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    public ResponseEntity<Object> activate(@PathVariable(required = false) Integer id) {
        return userService.activateUser(id);
    }

    @PostMapping("/deactivate/{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    public ResponseEntity<Object> deactivate(@PathVariable(required = false) Integer id) {
        return userService.deactivateUser(id);
    }
}
