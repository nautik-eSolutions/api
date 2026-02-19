package com.nautik.api.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ForbiddenToResourceException extends HttpClientErrorException {
    public ForbiddenToResourceException(String message) {
        super(HttpStatus.FORBIDDEN,message);
    }
}
