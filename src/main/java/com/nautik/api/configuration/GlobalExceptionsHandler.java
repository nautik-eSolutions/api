package com.nautik.api.configuration;

import com.nautik.api.domain.exceptions.ForbiddenToResourceException;
import com.nautik.api.domain.exceptions.NoAvailabilityException;
import com.nautik.api.domain.exceptions.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@Configuration
@ControllerAdvice
public class GlobalExceptionsHandler {


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolation(ConstraintViolationException ex) {
        ProblemDetail error = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        error.setProperty("timestamp", Instant.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleUserNotFoundException(ResourceNotFoundException ex) {
        ProblemDetail error = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        error.setProperty("timestamp", Instant.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(NoAvailabilityException.class)
    public ResponseEntity<ProblemDetail> handleNoAvailabilityException(NoAvailabilityException ex){
        ProblemDetail error = ProblemDetail.forStatusAndDetail(HttpStatus.NO_CONTENT, ex.getMessage());
        error.setProperty("timestamp", Instant.now());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(error);
    }

    @ExceptionHandler(ForbiddenToResourceException.class)
    public ResponseEntity<ProblemDetail> handleForbbidenToResourceException(ForbiddenToResourceException ex){
        ProblemDetail error = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        error.setProperty("timestamp", Instant.now());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }



}


