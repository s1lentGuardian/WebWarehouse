package org.kharkiv.khpi.model.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException
public class UsernamePasswordEmptyException extends RuntimeException{

    public UsernamePasswordEmptyException() {
        super("Username is empty");
    }
}
