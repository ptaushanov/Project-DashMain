package com.businessproject.demo.exeption;

public class PhoneNumberAlreadyExists extends Exception {
    @Override
    public String getMessage() {
        return "Phone number is used already!";
    }
}
