package com.example.tracker.exceptions;

public class UserExceptions extends RuntimeException{

    private int errorCode;

    public UserExceptions(String message, int code){
        super(message);
        errorCode = code;
    }

    public int getErrorCode(){
        return errorCode;
    }
}
