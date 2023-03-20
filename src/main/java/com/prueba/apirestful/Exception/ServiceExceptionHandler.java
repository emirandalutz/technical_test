package com.prueba.apirestful.Exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    public MessageException serviceError(ServiceException exception){
        MessageException messageException = new MessageException(exception.getMessage());
        return  messageException;
    }
    @ExceptionHandler
    public ResponseEntity<MessageException> handleEmailExist(DuplicateKeyException ex){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return responseError(ex.getMessage(),httpStatus);
    }
    @ExceptionHandler
    public ResponseEntity<MessageException> handleInfoInvalid(IllegalArgumentException ex){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return responseError(ex.getMessage(),httpStatus);
    }

    private ResponseEntity<MessageException> responseError(String message, HttpStatus httpStatus){
        return new ResponseEntity<>(new MessageException(message), httpStatus);
    }
}
