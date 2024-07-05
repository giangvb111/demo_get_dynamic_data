package com.example.demo.exception;

import com.example.demo.component.ResponseDataConfiguration;
import com.example.demo.constant.ResponseStatusConst;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ApiError> handleAllException(CommonException ex, WebRequest request) {
        ApiError error = new ApiError(ex.getErrorCode(), ex.getMessage(), ex.getErrorDetails());
        return ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, ex.getStatusCode());
    }
}
