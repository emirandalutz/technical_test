package com.prueba.apirestful.Exception;

public class ServiceException extends RuntimeException{

    public ServiceException(String message){
        super(message);
    }
}
