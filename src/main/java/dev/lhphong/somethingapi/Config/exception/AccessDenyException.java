package dev.lhphong.somethingapi.Config.exception;

public class AccessDenyException extends RuntimeException{
    public AccessDenyException(String message){
        super(message);
    }
}
