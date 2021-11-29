package com.tungstenautomationlab.tungstenautomationlab.modules.superadmin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SuperAdminRequestBody {
    private String username;
    private String password;
    private String newpassword;
}
