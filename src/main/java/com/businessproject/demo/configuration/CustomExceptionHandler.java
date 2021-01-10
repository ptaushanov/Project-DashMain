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

    @ExceptionHandler(BindException.class)
    public ModelAndView handleBindException(BindException exception) {
        ModelAndView errorMav = new ModelAndView("errors/databind_error");
        List<String> errors = exception
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        errorMav.addObject("model", Objects.requireNonNull(exception.getFieldError()).getObjectName());
        errorMav.addObject("errors", errors);
        return errorMav;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        return new ModelAndView("errors/request_param_error");
    }
}