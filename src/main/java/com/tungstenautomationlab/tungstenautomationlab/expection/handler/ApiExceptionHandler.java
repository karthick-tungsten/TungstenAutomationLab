package com.tungstenautomationlab.tungstenautomationlab.expection.handler;

import com.tungstenautomationlab.tungstenautomationlab.expection.ThrowApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ThrowApiError.class})
    public ResponseEntity<Object> handleExpection(ThrowApiError e){
        ErrorResponeBody errorResponeBody = new ErrorResponeBody(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(),e.getErrorCode());
        return new ResponseEntity<>(errorResponeBody,HttpStatus.BAD_REQUEST);
    }
}
