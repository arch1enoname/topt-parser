package com.arthur.exception.support;

public class ImageDownloadException extends Exception4Support {
    public ImageDownloadException() {
    }

    public ImageDownloadException(String message) {
        super(message);
    }

    public ImageDownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageDownloadException(Throwable cause) {
        super(cause);
    }

    public ImageDownloadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
