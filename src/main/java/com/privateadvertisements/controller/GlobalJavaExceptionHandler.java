package com.privateadvertisements.controller;

import com.privateadvertisements.exception.ApiError;
import com.privateadvertisements.exception.NotEntityException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice
public class GlobalJavaExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotEntityException.class, EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundEx(NotEntityException ex, WebRequest request) {
        ApiError apiError = new ApiError("Entity Not Found Exception", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError("Malformed JSON Request", ex.getMessage());
        return new ResponseEntity(apiError, status);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError = new ApiError("Method Argument Not Valid", ex.getMessage(), errors);
        return new ResponseEntity<>(apiError, status);
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(new ApiError("No Handler Found", ex.getMessage()), status);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


////    @ResponseBody
//    @ExceptionHandler(ConstraintViolationException.class)
////    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    protected ValidationErrorResponse onConstraintValidationException(
//            ConstraintViolationException e
//    ) {
//        final List<Violation> violations = e.getConstraintViolations().stream()
//                .map(
//                        violation -> new Violation(
//                                violation.getPropertyPath().toString(),
//                                violation.getMessage()
//                        )
//                )
//                .collect(Collectors.toList());
//        return new ValidationErrorResponse(violations);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
////    @ResponseStatus(HttpStatus.BAD_REQUEST)
////    @ResponseBody
//    protected ValidationErrorResponse onMethodArgumentNotValidException(
//            MethodArgumentNotValidException e
//    ) {
//        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
//                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
//                .collect(Collectors.toList());
//        return new ValidationErrorResponse(violations);
//    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError("Internal Exception", ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
