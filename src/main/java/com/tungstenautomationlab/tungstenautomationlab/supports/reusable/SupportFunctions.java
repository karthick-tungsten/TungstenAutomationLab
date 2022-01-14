package com.tungstenautomationlab.tungstenautomationlab.supports.reusable;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SupportFunctions {

    public String getCurrentDateWithDefaultFormat(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy, hh:mm:ss a"));
    }
}
