package com.tungstenautomationlab.tungstenautomationlab.superadmin;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    @PostMapping("api/v1/superAdmin/create")
    public Map<String, Object> createSuperAdmin(@RequestBody SuperAdminRequestBody superAdminRequestBody){
        return this.superAdminService.createSuperAdmin(superAdminRequestBody);
    }

    @PostMapping("api/v1/superAdmin/resetPassword")
    public Map<String, Object> resetPassword(@RequestBody SuperAdminRequestBody superAdminRequestBody){
        return this.superAdminService.resetPassword(superAdminRequestBody);
    }
}
