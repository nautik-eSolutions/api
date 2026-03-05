package com.nautik.api.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ForbiddenException extends HttpClientErrorException {
  public ForbiddenException(String message) {
    super(HttpStatus.FORBIDDEN,message);
  }
}
