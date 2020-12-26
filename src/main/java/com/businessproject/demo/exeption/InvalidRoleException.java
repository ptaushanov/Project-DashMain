package com.businessproject.demo.exeption;

public class InvalidRoleException extends  Exception{
    @Override
    public String getMessage(){
        return "Unknown value for a \"role\" was requested!";
    }
}
