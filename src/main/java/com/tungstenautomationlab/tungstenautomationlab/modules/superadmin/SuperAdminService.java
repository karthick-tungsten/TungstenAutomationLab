package com.tungstenautomationlab.tungstenautomationlab.modules.superadmin;

import com.tungstenautomationlab.tungstenautomationlab.supports.expection.ThrowApiError;
import com.tungstenautomationlab.tungstenautomationlab.supports.security.PasswordConfig;
import com.tungstenautomationlab.tungstenautomationlab.supports.security.Roles;
import com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement.UserDetailsRepository;
import com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement.Users;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@ToString
public class SuperAdminService {

    private final UserDetailsRepository userDetailsRepository;
    private final PasswordConfig passwordConfig;

    public Map<String, Object> createSuperAdmin(SuperAdminRequestBody body) {
        verifySuperAdminIsAlreadyAvailable();
        verifySuperAdminBody(body);
        Users users = new Users();
        users.setId(UUID.randomUUID().toString());
        users.setEmail(body.getUsername());
        users.setPassword(passwordConfig.passwordEncoder().encode(body.getPassword()));
        users.setFullName("Super Admin");
        users.setRole(Roles.SUPERADMIN.name());
        users.setCreatedOn(LocalDateTime.now().toString());
        userDetailsRepository.save(users);
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "super admin created successfully!");
        return response;
    }

    private void verifySuperAdminIsAlreadyAvailable() {
        List<Users> users = userDetailsRepository.getByRole("SUPERADMIN");
        if (users.size() > 0)
            throw new ThrowApiError("already one user admin available!", 1010, HttpStatus.ALREADY_REPORTED);
    }

    private void verifySuperAdminBody(SuperAdminRequestBody body) {
        if (body.getUsername().isEmpty()|| body.getUsername().length() < 3)
            throw new ThrowApiError("username can't be blank", 1012, HttpStatus.BAD_REQUEST);
        if (body.getPassword().isEmpty() || body.getPassword().length() < 5)
            throw new ThrowApiError("password can't be blank and less than 5 characters", 1013, HttpStatus.BAD_REQUEST);
    }

    public Map<String, Object> resetPassword(SuperAdminRequestBody body) {
        Users users = findSuperAdmin(body.getUsername());
        users.setPassword(passwordConfig.passwordEncoder().encode(body.getNewpassword()));
        userDetailsRepository.save(users);
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "super admin password reset successfully!");
        return response;
    }

    private Users findSuperAdmin(String username) {
        List<Users> users = userDetailsRepository.findByEmail(username);
        if (users.size()==0)
            throw new ThrowApiError("invalid super admin username", 1011, HttpStatus.NOT_FOUND);
        else
            return users.get(0);
    }
}

