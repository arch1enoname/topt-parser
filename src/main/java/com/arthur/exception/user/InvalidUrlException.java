package com.arthur.exception.user;

public class InvalidUrlException extends Exception4User{

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
