package com.prueba.apirestful.Exception;

public class MessageException {
    private String message;

    public MessageException(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
