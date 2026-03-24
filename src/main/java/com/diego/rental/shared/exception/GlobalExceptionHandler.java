package com.diego.rental.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ApiError handleBusinessException(BusinessException ex){
        return new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(Exception.class)
    public ApiError handleGenericException(Exception ex){
        return new ApiError("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


}
