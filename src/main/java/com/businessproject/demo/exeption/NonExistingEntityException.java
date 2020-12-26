package com.businessproject.demo.exeption;

public class NonExistingEntityException extends  Exception{

    @Override
    public String getMessage(){
        return "Entity does not exist!";
    }
}
