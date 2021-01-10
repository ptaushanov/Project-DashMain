package com.businessproject.demo.configuration;

public interface IValidationPatterns {
    String NAME_PATTERN = "^[A-Z][a-z]+$";
    String USERNAME_PATTERN = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
}
