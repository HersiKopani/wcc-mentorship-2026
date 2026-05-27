package com.wcc.hospital.exception;

import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PatientNotFoundException.class)
    ProblemDetail handlePatientNotFound(PatientNotFoundException ex, WebRequest request) {
        return problem(HttpStatus.NOT_FOUND, "Patient Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(DuplicateNationalIdException.class)
    ProblemDetail handleDuplicateNationalId(DuplicateNationalIdException ex, WebRequest request) {
        return problem(HttpStatus.CONFLICT, "Duplicate National ID", ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleGeneric(Exception ex, WebRequest request) {
        return problem(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                "An unexpected error occurred", request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,
                        (existing, replacement) -> existing));

        ProblemDetail pd = problem(HttpStatus.BAD_REQUEST, "Validation Failed",
                "One or more fields failed validation", request);
        pd.setProperty("errors", fieldErrors);
        return ResponseEntity.badRequest().body(pd);
    }

    private ProblemDetail problem(HttpStatus status, String title, String detail, WebRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detail);
        pd.setTitle(title);
        pd.setType(URI.create("https://wcc-hospital.dev/errors/" + title.toLowerCase().replace(" ", "-")));
        pd.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }
}
