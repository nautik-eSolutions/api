package com.nautik.api.domain.exceptions;

public class NoAvailabilityException extends RuntimeException {
    public NoAvailabilityException() {

        super("No availability for the requested dates");
    }
}
