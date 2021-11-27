package com.tungstenautomationlab.tungstenautomationlab.userdetailsmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserDetailsManagementController {

    private final UserDetailsManagementService userDetailsService;

    @Autowired
    public UserDetailsManagementController(UserDetailsManagementService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("api/v1/createUser")
    @PreAuthorize("hasAnyRole('SUPERADMIN')")
    public Map<String, Object> createUser(@RequestBody UserCreateRquestBody createRquestBody){
        return userDetailsService.createUser(createRquestBody);
    }


}
