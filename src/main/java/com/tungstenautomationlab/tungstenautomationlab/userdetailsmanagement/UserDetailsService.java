package com.tungstenautomationlab.tungstenautomationlab.userdetailsmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserDetailsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    public Map<String, Object> createUser(UserCreateRquestBody requestBody) {
        validateRequestBody(requestBody);

        Users user = new Users();
        user.setId(UUID.randomUUID().toString());
        user.setFullName(requestBody.getFullName());
        user.setEmail(requestBody.getEmail());
        user.setPassword(requestBody.getPassword());
        user.setRole(requestBody.getRole());
        userDetailsRepository.save(user);

        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("message", "user created successfully!");
        return map;
    }

    private void validateRequestBody(UserCreateRquestBody requestBody) {
        if (requestBody.getFullName().length() < 3) {
            throw new IllegalArgumentException("name should not be less than 3 character");
        }
    }
}
