package org.kharkiv.khpi.model.exception;

public class UsernamePasswordEmptyException extends RuntimeException{

    public UsernamePasswordEmptyException() {
        super("Username is empty");
    }
}
