package com.tungstenautomationlab.tungstenautomationlab.expection;

import org.springframework.http.HttpStatus;

public class ThrowApiError extends RuntimeException{

    private final int errorCode;
    private final HttpStatus status;

    public ThrowApiError(String message, int errorCode, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public ThrowApiError(String message, Throwable cause, int errorCode, HttpStatus status) {
        super(message, cause);
        this.errorCode = errorCode;
        this.status = status;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
