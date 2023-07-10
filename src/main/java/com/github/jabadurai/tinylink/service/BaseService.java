package com.github.jabadurai.tinylink.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BaseService {

    public ResponseEntity<Object> checkIfAnyErrorsInObject(Integer id, BindingResult bindingResult, List<String> updatableFields) {
        List<FieldError> errors = new ArrayList<>(bindingResult.getFieldErrors());
        if (bindingResult.hasErrors()) {
            if(id != null){
                errors = bindingResult.getFieldErrors().stream().filter(e -> updatableFields.contains(e.getObjectName())).collect(Collectors.toList());
            }
            // handle errors here, you might want to return them in the response body
            if(errors.size() > 0){
                return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
            }
        }
        return null;
    }

}
