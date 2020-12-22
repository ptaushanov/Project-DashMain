package com.businessproject.demo.exeption;

public class InvalidRoleException extends  Exception{
    @Override
    public String getMessage(){
        return "Invalid role provided!";
    }
}
