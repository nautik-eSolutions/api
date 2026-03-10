package com.nautik.api.domain.exceptions;

public class UserHasABooking extends RuntimeException {
    public UserHasABooking() {
        super("User has a booking in those dates");
    }
}
