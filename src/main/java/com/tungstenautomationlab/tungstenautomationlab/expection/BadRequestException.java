package com.tungstenautomationlab.tungstenautomationlab.expection;

public class BadRequestException extends RuntimeException{

    private final int errorCode;

    public BadRequestException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BadRequestException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
