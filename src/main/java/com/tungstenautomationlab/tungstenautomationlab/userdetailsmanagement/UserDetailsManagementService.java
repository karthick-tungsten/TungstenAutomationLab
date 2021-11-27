package com.tungstenautomationlab.tungstenautomationlab.userdetailsmanagement;

import com.tungstenautomationlab.tungstenautomationlab.expection.ThrowApiError;
import com.tungstenautomationlab.tungstenautomationlab.security.PasswordConfig;
import com.tungstenautomationlab.tungstenautomationlab.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        validateRequestBody(requestBody);
        verifyUserAlreadyExists(requestBody.getEmail());
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

    private boolean validateEmailFormat(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    private void verifyUserAlreadyExists(String email) {
        if (userDetailsRepository.findByEmail(email).size() != 0) {
            throw new ThrowApiError("email id already exists", 1005, HttpStatus.BAD_REQUEST);
        }
    }

    private void validateRequestBody(UserCreateRquestBody requestBody) {
        if (requestBody.getFullName().length() < 3 || !requestBody.getFullName().matches("^[a-zA-Z]*$"))
            throw new ThrowApiError("name cannot be less than 3 characters and should not contains numbers", 1001, HttpStatus.BAD_REQUEST);
        if (requestBody.getEmail().isEmpty() || !validateEmailFormat(requestBody.getEmail()))
            throw new ThrowApiError("invalid email format", 1002, HttpStatus.BAD_REQUEST);
        if (requestBody.getPassword().isEmpty() || requestBody.getPassword().length() < 5)
            throw new ThrowApiError("invalid password", 1003, HttpStatus.BAD_REQUEST);
        try {
            if (Roles.SUPERADMIN == Roles.valueOf(requestBody.getRole())) {
                throw new IllegalArgumentException();
            }
            Roles.valueOf(requestBody.getRole());
        } catch (IllegalArgumentException e) {
            throw new ThrowApiError("invalid role", 1004, HttpStatus.BAD_REQUEST);
        }
    }

}
