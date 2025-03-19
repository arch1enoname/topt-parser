package com.arthur.exception;

public class InvalidUrlException extends RuntimeException{

    public InvalidUrlException() {
    }

    public InvalidUrlException(String message) {
        super(message);
    }

    public InvalidUrlException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUrlException(Throwable cause) {
        super(cause);
    }

    public InvalidUrlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
