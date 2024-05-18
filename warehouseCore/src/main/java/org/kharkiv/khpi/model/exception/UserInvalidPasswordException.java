package org.kharkiv.khpi.model.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException
public class UserInvalidPasswordException extends RuntimeException{

    public UserInvalidPasswordException() {
        super("Invalid password");
    }
}
