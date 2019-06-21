package com.jpotify.logic.exceptions;

public class NoTagFoundException extends Exception {

    public NoTagFoundException() {
        super();
    }

    public NoTagFoundException(String message) {
        super(message);
    }

    public NoTagFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
