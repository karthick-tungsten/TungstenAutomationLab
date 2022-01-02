package com.tungstenautomationlab.tungstenautomationlab.supports.simpleresponse;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
@Getter
@Setter
@AllArgsConstructor
public class SimpleResponse {
    private String message;
    private HttpStatus httpStatus;
}
