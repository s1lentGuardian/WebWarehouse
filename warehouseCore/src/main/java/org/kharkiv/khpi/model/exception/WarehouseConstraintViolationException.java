package org.kharkiv.khpi.model.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.Set;

public class WarehouseConstraintViolationException extends ConstraintViolationException {

    public WarehouseConstraintViolationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
    }
}
