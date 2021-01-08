package com.businessproject.demo.exeption;

public class InsufficientQuantityException extends Exception {
    @Override
    public String getMessage() {
        return "Not enough product to fulfil the order!";
    }
}
