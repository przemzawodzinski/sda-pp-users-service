package com.sda.exception;

public class UsernameConflictException extends RuntimeException{
    public UsernameConflictException (String message){
        super(message);
    }
}
