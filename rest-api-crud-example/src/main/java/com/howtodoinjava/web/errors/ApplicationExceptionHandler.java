package com.howtodoinjava.web.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApplicationExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ResponseEntity<ProblemDetail> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    List<String> details = new ArrayList<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      details.add(error.getDefaultMessage());
    }
    ProblemDetail body = ProblemDetail
        .forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getLocalizedMessage());
    body.setType(URI.create("http://my-app-host.com/errors/bad-request"));
    body.setTitle("Bad Request");
    body.setProperty("details", details);
    return ResponseEntity.badRequest()
        .body(body);
  }

  @ExceptionHandler(ItemNotFoundException.class)
  public ProblemDetail handleItemNotFoundException(
      ItemNotFoundException ex, WebRequest request) {

    ProblemDetail body = ProblemDetail
        .forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getLocalizedMessage());
    body.setType(URI.create("http://my-app-host.com/errors/not-found"));
    body.setTitle("Item Not Found");
    body.setProperty("hostname", "localhost");
    return body;
  }
}
