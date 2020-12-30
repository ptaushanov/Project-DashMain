package com.businessproject.demo.exeption;

public class NonExistingCustomerException extends Exception {
    @Override
    public String getMessage() {
        return "Customer does not exist!";
    }
}
