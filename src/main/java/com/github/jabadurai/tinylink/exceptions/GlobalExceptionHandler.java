package com.github.jabadurai.tinylink.exceptions;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("oops"); // use your error page here
        mav.addObject("error", e.getMessage());
        return mav;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(AccessDeniedException e) {
        ModelAndView mav = new ModelAndView("oops"); // redirect to your access denied page
        mav.addObject("error", e.getMessage() + ", You don't have access to this action/module/site.");
        return mav;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNoHandlerFoundException(NoHandlerFoundException e) {
        ModelAndView mav = new ModelAndView("oops"); // use your error page here
        mav.addObject("error", "Page you are trying to access is not valid: " + e.getRequestURL());
        return mav;
    }

}
