package com.businessproject.demo.exeption;

public class AuthorizationException extends Exception{
    @Override public String getMessage(){
        return "Non authorized access is forbidden!";
    }
}
