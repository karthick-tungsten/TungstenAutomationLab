package com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement.requestbody;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateUserRequestBody {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String role;
}
