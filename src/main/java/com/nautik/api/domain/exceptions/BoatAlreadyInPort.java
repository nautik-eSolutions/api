package com.nautik.api.domain.exceptions;

public class BoatAlreadyInPort extends RuntimeException {
    public BoatAlreadyInPort() {
        super("This boat is already staying in the port within those dates");
    }
}
