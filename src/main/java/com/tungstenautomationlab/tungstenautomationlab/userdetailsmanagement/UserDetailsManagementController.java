package com.tungstenautomationlab.tungstenautomationlab.userdetailsmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserDetailsManagementController {

    private final UserDetailsService userDetailsService;

    @Autowired
    public UserDetailsManagementController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("api/v1/createUser")
    public Map<String, Object> createUser(@RequestBody UserCreateRquestBody requestBody) {
        return userDetailsService.createUser(requestBody);
    }
}
