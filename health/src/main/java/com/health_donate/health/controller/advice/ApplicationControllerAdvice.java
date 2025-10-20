package com.health_donate.health.controller.advice;


import com.health_donate.health.dto.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.SignatureException;
import java.time.Instant;
import java.time.LocalDateTime;

@ControllerAdvice
public class ApplicationControllerAdvice  {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception, HttpServletRequest request) {
        if (exception instanceof BadCredentialsException e) {
            return createProblemDetail(401, e.getMessage(), "The username or password is incorrect", request.getRequestURI());
        } else if (exception instanceof AccountStatusException e) {
            return createProblemDetail(403, e.getMessage(), "The account is locked", request.getRequestURI());
        } else if (exception instanceof AccessDeniedException e) {
            return createProblemDetail(403, e.getMessage(), "You are not authorized to access this resource", request.getRequestURI());
        } else if (exception instanceof SignatureException e) {
            return createProblemDetail(401, e.getMessage(), "The JWT signature is invalid", request.getRequestURI());
        } else if (exception instanceof ExpiredJwtException e) {
            return createProblemDetail(401, e.getMessage(), "The JWT token has expired", request.getRequestURI());
        } else {
            return createProblemDetail(500, exception.getMessage(), "Unknown internal server error.", request.getRequestURI());
        }
    }

    private ProblemDetail createProblemDetail(int status, String message, String description, String path) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status), message);
        detail.setProperty("description", description);
        detail.setProperty("path", path);
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException except,
            HttpServletRequest request) {
        String message = except.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                .orElse("Validation error");

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @ExceptionHandler({
            RuntimeException.class,
            EntityNotFoundException.class,

            SecurityException.class})
    public @ResponseBody ResponseEntity<ErrorResponse> handleRuntimeException(
            RuntimeException except,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(
                        LocalDateTime.now(),
                        String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        except.getMessage(),
                        request.getRequestURI()

                ));

    }
}
