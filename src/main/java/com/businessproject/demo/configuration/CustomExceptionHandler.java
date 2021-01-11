package com.businessproject.demo.configuration;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {

    // A custom exception handler for all binding
    // exceptions thrown by Hibernate's validation
    @ExceptionHandler(BindException.class)
    public ModelAndView handleBindException(BindException exception) {
        // Creating a model and view object from template "databind_errors"
        ModelAndView errorMav = new ModelAndView("errors/databind_error");

        // Extracting all error messages from the exception that are
        // from the "message" attribute of all validation annotations
        List<String> errors = exception
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        // Adding attribute "model", that is the model on which the validation failed
        errorMav.addObject("model", Objects.requireNonNull(exception.getFieldError()).getObjectName());
        // Adding all the errors to an attribute called errors for later use of the template engine
        errorMav.addObject("errors", errors);

        return errorMav;
    }

    // Custom exception handler for all missing required request parameters
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        // Returning our custom model and view object from template "request_param_error"
        return new ModelAndView("errors/request_param_error");
    }
}