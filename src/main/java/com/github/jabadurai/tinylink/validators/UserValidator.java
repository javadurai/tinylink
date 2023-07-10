package com.github.jabadurai.tinylink.validators;

import com.github.jabadurai.tinylink.entities.User;
import com.github.jabadurai.tinylink.utils.StringUtils;
import jakarta.validation.ValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserValidator implements Validator {

    private static final String EMAIL_PATTERN =
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    @Autowired
    private LocalValidatorFactoryBean validatorFactory;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "NotEmpty.userForm.fullName",null, "Full name is mandatory");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.userForm.username",null, "Username is mandatory");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.userForm.email",null, "Email is mandatory");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userForm.password",null, "Password is mandatory");

        User user = (User) target;

        // TODO - Fix Email Validation
//        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(user.getEmail());
//        if(matcher.matches()){
//            errors.rejectValue("email", "NotValid.userForm.email", "Email is not valid");
//        }

        if(StringUtils.isEmpty(user.getPassword())){
            errors.rejectValue("password", "NotEmpty.userForm.password", "Password is mandatory");
        }

    }
}
