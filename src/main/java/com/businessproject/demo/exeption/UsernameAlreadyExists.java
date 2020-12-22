package com.businessproject.demo.exeption;

public class UsernameAlreadyExists extends Exception{
    @Override
    public String getMessage(){
        return "Username already exist in Database";
    }
}
