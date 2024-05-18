package org.kharkiv.khpi.model.exception;

import jakarta.ejb.ApplicationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.Set;

@ApplicationException
public class WarehouseConstraintViolationException extends ConstraintViolationException {

    public WarehouseConstraintViolationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
    }
}
