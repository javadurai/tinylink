package com.github.jabadurai.tinylink.validators;

import com.github.jabadurai.tinylink.dto.ChangePasswordRequest;
import com.github.jabadurai.tinylink.entities.CustomUserDetails;
import com.github.jabadurai.tinylink.service.UserService;
import com.github.jabadurai.tinylink.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Service
public class ChangePasswordValidator implements Validator {

    private UserService userService;

    public ChangePasswordValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ChangePasswordRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ChangePasswordRequest changePasswordRequest = (ChangePasswordRequest) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPassword", "NotEmpty.changePasswordForm.currentPassword",null, "Current Password is mandatory");

        // Check current password provided is correct
        Optional<CustomUserDetails> currentUser = userService.currentLoggedInUser();
        if(currentUser.isPresent() && !currentUser.get().getPassword().equals(changePasswordRequest.getCurrentPassword())){
            errors.rejectValue("currentPassword", "NotCorrect.changePasswordForm.currentPassword", "Your current password is not matching with our records");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "NotEmpty.changePasswordForm.newPassword",null, "New Password is mandatory");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.changePasswordForm.confirmPassword",null, "Confirm New Password is mandatory");


        if (StringUtils.isNotEmpty(changePasswordRequest.getNewPassword()) && StringUtils.isNotEmpty(changePasswordRequest.getConfirmPassword())
                && !changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())){
            errors.rejectValue("confirmPassword", "NotMatching.changePasswordForm.confirmPassword", "New password and Confirm password are not matching.");
        }

        if (StringUtils.isNotEmpty(changePasswordRequest.getCurrentPassword()) && StringUtils.isNotEmpty(changePasswordRequest.getNewPassword())
                && changePasswordRequest.getNewPassword().equals(changePasswordRequest.getCurrentPassword())){
            errors.rejectValue("newPassword", "NotMatching.changePasswordForm.newPassword", "You cannot have new password as same as your current password");
        }

    }
}
