package com.example.exception;
/**
 * General purpose exception that was used for HttpStatus.CONFLIT for the {@link SocialMediaController}
 */
public class ExceptionAndErrorController extends RuntimeException{
    public ExceptionAndErrorController(String message) {
        super(message);
    }
}
