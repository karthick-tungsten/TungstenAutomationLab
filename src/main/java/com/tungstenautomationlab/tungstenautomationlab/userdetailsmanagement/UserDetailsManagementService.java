package com.tungstenautomationlab.tungstenautomationlab.userdetailsmanagement;

import com.tungstenautomationlab.tungstenautomationlab.expection.ThrowApiError;
import com.tungstenautomationlab.tungstenautomationlab.expection.handler.ApiExceptionHandler;
import com.tungstenautomationlab.tungstenautomationlab.security.PasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserDetailsManagementService {

    private final UserDetailsRepository userDetailsRepository;
    private final PasswordConfig passwordConfig;

    @Autowired
    public UserDetailsManagementService(UserDetailsRepository userDetailsRepository, PasswordConfig passwordConfig) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordConfig = passwordConfig;
    }

    public Map<String, Object> createUser(UserCreateRquestBody requestBody) {
        verifyUserAlreadyExists(requestBody.getEmail());
        validateRequestBody(requestBody);

        Users user = new Users();
        user.setId(UUID.randomUUID().toString());
        user.setFullName(requestBody.getFullName());
        user.setEmail(requestBody.getEmail());
        user.setPassword(passwordConfig.passwordEncoder().encode(requestBody.getPassword()));
        user.setRole(requestBody.getRole());

        userDetailsRepository.save(user);

        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("message", "user created successfully!");
        return map;
    }

    private void verifyUserAlreadyExists(String email) {
        if (userDetailsRepository.findByEmail(email).size() != 0) {
            throw new ThrowApiError("email id already exists", 1005, HttpStatus.BAD_REQUEST);
        }
    }

    private void validateRequestBody(UserCreateRquestBody requestBody) {
        if (requestBody.getFullName().length() < 3) {
            throw new ThrowApiError("name cannot be less than 3 characters", 1001, HttpStatus.BAD_REQUEST);
        }
    }

    public List<Users> getAll() {
        return userDetailsRepository.findAll();
    }
}
