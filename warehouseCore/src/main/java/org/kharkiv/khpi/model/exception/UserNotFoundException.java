package org.kharkiv.khpi.model.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
