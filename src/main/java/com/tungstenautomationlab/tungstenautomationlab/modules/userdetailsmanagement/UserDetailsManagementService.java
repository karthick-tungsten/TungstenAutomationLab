package com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement;

import com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement.requestbody.UpdateUserRequestBody;
import com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement.requestbody.UserCreateRquestBody;
import com.tungstenautomationlab.tungstenautomationlab.supports.expection.ThrowApiError;
import com.tungstenautomationlab.tungstenautomationlab.supports.security.PasswordConfig;
import com.tungstenautomationlab.tungstenautomationlab.supports.security.Roles;
import com.tungstenautomationlab.tungstenautomationlab.supports.security.TokenDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserDetailsManagementService {

    private final UserDetailsRepository userDetailsRepository;
    private final PasswordConfig passwordConfig;
    private final TokenDetails tokenDetails;

    @Autowired
    public UserDetailsManagementService(UserDetailsRepository userDetailsRepository, PasswordConfig passwordConfig, TokenDetails tokenDetails) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordConfig = passwordConfig;
        this.tokenDetails = tokenDetails;
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
        user.setCreatedOn(LocalDateTime.now().toString());
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

    /***
     * fullname created  for onespace between username in first if statement
     * @param requestBody
     */
    private void validateRequestBody(UserCreateRquestBody requestBody) {
        if (requestBody.getFullName().length() < 3 || !requestBody.getFullName().matches("^[a-zA-z]+([\\s][a-zA-Z]+)*$"))
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

    public Map<String, String> getUserDetails() {
        String userId = tokenDetails.getUserId();
        Users user = userDetailsRepository.findById(userId).get();
        Map<String, String> map = new HashMap<>();
        map.put("fullName", user.getFullName());
        map.put("email", user.getEmail());
        map.put("role", user.getRole());
        map.put("createdOn", user.getCreatedOn());
        map.put("lastUpdate", user.getLastUpdate());
        return map;
    }

    /***
     * delete user with id parameter and display error mssage
     * @param id
     * @return
     */
    public Map<String, Object> deleteUser(String id) {
        Map<String, Object> map = new HashMap<>();
        Optional<Users> user = userDetailsRepository.findById(id);
        if (user.isPresent()) {
            userDetailsRepository.delete(user.get());
            map.put("status", 200);
            map.put("message", "user delete successfully!");
        } else {
            map.put("status", 400);
            map.put("message", "user not found");
            map.put("timestamp", LocalDateTime.now().toString());
            map.put("errorcode", "1030");
        }
        return map;
    }

    public Map<String, String> getUserDetailsUpdate() {
        String userId = tokenDetails.getUserId();
        Users user = userDetailsRepository.findById(userId).get();
        Map<String, String> map = new HashMap<>();
        map.put("createdOn", user.getCreatedOn());
        map.put("lastUpdate", user.getLastUpdate());
        return map;
    }

    public Map<String, Object> updateUserDetails(UpdateUserRequestBody body) {
        Map<String, Object> response = new HashMap<>();
        String updateDetails = "";
        int saveFlag = 0;
        Optional<Users> user = userDetailsRepository.findById(body.getUserId());
        if (!user.isPresent()) {
            throw new ThrowApiError("user not found", 1006, HttpStatus.NOT_FOUND);
        } else {
            Users _user = user.get();
            if (body.getName() != null && !body.getName().equals("") && !body.getName().equals(_user.getFullName())) {
                _user.setFullName(body.getName());
                saveFlag++;
                updateDetails += "fullName,";
            }
            if (body.getEmail() != null && !body.getEmail().equals("") && !body.getEmail().equals(_user.getEmail())) {
                _user.setEmail(body.getEmail());
                saveFlag++;
                updateDetails += "email,";
            }
            if (body.getPassword() != null && !body.getPassword().equals("")) {
                if(body.getPassword().length() < 5){
                    throw new ThrowApiError("password can't be less than 5", 1009, HttpStatus.BAD_REQUEST);
                }
                _user.setPassword(passwordConfig.passwordEncoder().encode(body.getPassword()));
                saveFlag++;
                updateDetails += "password,";
            }
            try {
                if (body.getRole() != null && !body.getRole().equals("") && !Roles.valueOf(body.getRole()).equals(Roles.valueOf(_user.getRole()))) {
                    if (Roles.valueOf(body.getRole()).equals(Roles.SUPERADMIN)) {
                        throw new ThrowApiError("invalid role", 1008, HttpStatus.NOT_FOUND);
                    }
                    _user.setRole(Roles.valueOf(body.getRole()).name());
                    saveFlag++;
                    updateDetails += "role,";
                }
            } catch (Exception e) {
                throw new ThrowApiError("invalid role", 1007, HttpStatus.NOT_FOUND);
            }
            if (saveFlag != 0) {
                userDetailsRepository.save(_user);
                response.put("status", 200);
                response.put("message", updateDetails.substring(0,updateDetails.length()-1) + " updated successfully");
                return response;
            }else{
                response.put("status", 400);
                response.put("message", "nothing updated");
                return response;
            }
        }

    }
}
