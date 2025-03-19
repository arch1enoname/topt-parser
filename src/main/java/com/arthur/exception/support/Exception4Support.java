package com.arthur.exception.support;

public class Exception4Support extends RuntimeException{
    public Exception4Support() {
    }

    public Exception4Support(String message) {
        super(message);
    }

    public Exception4Support(String message, Throwable cause) {
        super(message, cause);
    }

    public Exception4Support(Throwable cause) {
        super(cause);
    }

    public Exception4Support(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
