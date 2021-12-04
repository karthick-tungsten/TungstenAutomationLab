package com.tungstenautomationlab.tungstenautomationlab.supports.security;

import org.springframework.stereotype.Component;

@Component
public class TokenDetails {

    TokenDetails(){
    }
    private String userId;

    public void setUserId(String userId){
        this.userId=userId;
    }

    public String getUserId(){
        return userId;
    }
}
