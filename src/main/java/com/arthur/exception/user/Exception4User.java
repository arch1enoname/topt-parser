package com.arthur.exception.user;

public class Exception4User extends RuntimeException{
    public Exception4User() {
    }

    public Exception4User(String message) {
        super(message);
    }

    public Exception4User(String message, Throwable cause) {
        super(message, cause);
    }

    public Exception4User(Throwable cause) {
        super(cause);
    }

    public Exception4User(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
