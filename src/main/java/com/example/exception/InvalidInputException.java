package com.example.exception;
/**
 * This exception is for covering when the client sends information that would not work for the system
 * Like password being a length of less then 4, or a message text to be less than 255
 */
public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String message){
        super(message); 
    }
    
}
