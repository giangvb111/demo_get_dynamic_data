package com.example.demo.component;

import com.example.demo.exception.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

public class ResponseDataConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    public static <T> ResponseEntity<T> error(Integer status, ApiError error, HttpStatus httpStatus) {
        ResponseData<T> responseData = new ResponseData<>(status, error);
        return new ResponseEntity(responseData, httpStatus);
    }
}
