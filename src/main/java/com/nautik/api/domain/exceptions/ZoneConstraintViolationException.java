package com.nautik.api.domain.exceptions;

import org.hibernate.exception.ConstraintViolationException;

public class ZoneConstraintViolationException extends RuntimeException {
    public ZoneConstraintViolationException() {
        super("Zone cannot be deleted because it has mooring categories");
    }
}
