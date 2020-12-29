package com.businessproject.demo.exeption;

public class NonExistingProductException extends Exception {
    @Override
    public String getMessage(){
        return "Product does not exist!";
    }
}
