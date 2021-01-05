package com.businessproject.demo.exeption;

public class PromotionAlreadyActive extends Exception {
    @Override
    public String getMessage() {
        return "There is already an ongoing promotion.";
    }
}
