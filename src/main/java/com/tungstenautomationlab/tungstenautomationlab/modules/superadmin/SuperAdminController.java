package com.tungstenautomationlab.tungstenautomationlab.modules.superadmin;

import com.tungstenautomationlab.tungstenautomationlab.modules.superadmin.requestbody.SuperAdminRequestBody;
import com.tungstenautomationlab.tungstenautomationlab.modules.superadmin.responsebody.GetAllProjectsResponse;
import com.tungstenautomationlab.tungstenautomationlab.modules.superadmin.responsebody.GetAllUsersResponse;
import com.tungstenautomationlab.tungstenautomationlab.supports.simpleresponse.SimpleResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/superAdmin")
@AllArgsConstructor
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    @PostMapping(path = "create")
    public Map<String, Object> createSuperAdmin(@RequestBody SuperAdminRequestBody superAdminRequestBody){
        return this.superAdminService.createSuperAdmin(superAdminRequestBody);
    }

    @PostMapping(path = "resetPassword")
    public Map<String, Object> resetPassword(@RequestBody SuperAdminRequestBody superAdminRequestBody){
        return this.superAdminService.resetPassword(superAdminRequestBody);
    }

    @GetMapping(path = "getAllUsers")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public GetAllUsersResponse getAllUsers(){
        return superAdminService.getAllUsers();
    }


    @GetMapping(path = "getAllProjects")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public GetAllProjectsResponse getAllProjects(){
        return superAdminService.getAllProjects();
    }

    @GetMapping(path = "homePageNavigation")
    public SimpleResponse homePageNavigation(){
        return this.superAdminService.getSuperadminDetails();
    }
}
