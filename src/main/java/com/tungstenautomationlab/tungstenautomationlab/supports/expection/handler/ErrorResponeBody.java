package com.tungstenautomationlab.tungstenautomationlab.supports.expection.handler;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ErrorResponeBody {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timeStamp;
    private final int errorCode;

    public ErrorResponeBody(String message, HttpStatus httpStatus, ZonedDateTime timeStamp, int errorCode) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
