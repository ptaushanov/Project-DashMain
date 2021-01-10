package com.businessproject.demo.exeption;

public class ProductNameExistsException extends Exception {
    @Override
    public String getMessage() {
        return "Provided product name was used for another product!";
    }
}
