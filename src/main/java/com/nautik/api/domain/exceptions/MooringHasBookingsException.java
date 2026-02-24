package com.nautik.api.domain.exceptions;

public class MooringHasBookingsException extends RuntimeException {
    public MooringHasBookingsException() {
        super("Mooring cannot be deleted because it has bookings");
    }
}
