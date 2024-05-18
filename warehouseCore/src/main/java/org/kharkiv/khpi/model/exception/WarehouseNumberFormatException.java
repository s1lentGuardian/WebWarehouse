package org.kharkiv.khpi.model.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException
public class WarehouseNumberFormatException extends RuntimeException{

    public WarehouseNumberFormatException(String message) {
        super(message);
    }
}
