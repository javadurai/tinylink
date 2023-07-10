package com.github.jabadurai.tinylink.controllers;

import com.github.jabadurai.tinylink.entities.Url;
import com.github.jabadurai.tinylink.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/urls")
public class UrlRestController {

    private final UrlService urlService;

    public UrlRestController(UrlService urlService){
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<Object> saveUrl(@Valid @RequestBody Url urlDto, BindingResult bindingResult) {

        return urlService.saveLink(urlDto, bindingResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = false) Integer id) {
        return urlService.deleteLink(id);
    }
}
