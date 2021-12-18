package com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement;

import com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement.requestbody.UpdateUserRequestBody;
import com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement.requestbody.UserCreateRquestBody;
import com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement.responsebody.GetUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public Map<String, Object> createUser(@RequestBody UserCreateRquestBody createRquestBody) {
        return userDetailsService.createUser(createRquestBody);
    }

    @GetMapping("api/v1/getUserDetails")
    public GetUserResponse getUserDetails(){
        return userDetailsService.getUserDetails();
    }

    /***
     * deletemapping for delete user form database
     * @param id
     * @return
     */
    @DeleteMapping("api/v1/deleteUser/{id}")
    @PreAuthorize("hasAnyRole('SUPERADMIN')")
    public Map<String, Object> deleteUser(@PathVariable("id")  String id){
       return userDetailsService.deleteUser(id);
    }

    @PutMapping("api/v1/updateUserDetails")
    @PreAuthorize("hasAnyRole('SUPERADMIN')")
    public Map<String, Object> updateUserDetails(@RequestBody UpdateUserRequestBody body){
        return userDetailsService.updateUserDetails(body);
    }
}
