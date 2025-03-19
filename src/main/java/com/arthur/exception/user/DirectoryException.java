package com.arthur.exception.user;

public class DirectoryException extends Exception4User {
    public DirectoryException() {
    }

    public DirectoryException(String message) {
        super(message);
    }

    public DirectoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectoryException(Throwable cause) {
        super(cause);
    }

    public DirectoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
