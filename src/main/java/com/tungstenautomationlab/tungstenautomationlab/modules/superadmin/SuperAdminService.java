package com.tungstenautomationlab.tungstenautomationlab.modules.superadmin;

import com.tungstenautomationlab.tungstenautomationlab.modules.project.Project;
import com.tungstenautomationlab.tungstenautomationlab.modules.project.ProjectRepository;
import com.tungstenautomationlab.tungstenautomationlab.modules.projectassignment.ProjectAssignmentRepository;
import com.tungstenautomationlab.tungstenautomationlab.modules.superadmin.requestbody.SuperAdminRequestBody;
import com.tungstenautomationlab.tungstenautomationlab.modules.superadmin.responsebody.GetAllProjectsResponse;
import com.tungstenautomationlab.tungstenautomationlab.modules.superadmin.responsebody.GetAllUsersResponse;
import com.tungstenautomationlab.tungstenautomationlab.modules.superadmin.responsebody.UserDetailsWithoutPassword;
import com.tungstenautomationlab.tungstenautomationlab.supports.expection.ThrowApiError;
import com.tungstenautomationlab.tungstenautomationlab.supports.security.PasswordConfig;
import com.tungstenautomationlab.tungstenautomationlab.supports.security.Roles;
import com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement.UserDetailsRepository;
import com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement.Users;
import com.tungstenautomationlab.tungstenautomationlab.supports.security.TokenDetails;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;

@Service
@AllArgsConstructor
public class SuperAdminService {

    private final UserDetailsRepository userDetailsRepository;
    private final PasswordConfig passwordConfig;
    private final ProjectRepository projectRepository;
    private final TokenDetails tokenDetails;
    private final ProjectAssignmentRepository assignmentRepository;

    /***
     * method to create super admin
     * @param body - SuperAdminRequestBody separate class to handle the request from api call
     * @return
     */
    public Map<String, Object> createSuperAdmin(SuperAdminRequestBody body) {
        verifySuperAdminIsAlreadyAvailable();
        verifySuperAdminBody(body);
        Users users = new Users();
        users.setId(UUID.randomUUID().toString());
        users.setEmail(body.getUsername());
        users.setPassword(passwordConfig.passwordEncoder().encode(body.getPassword()));
        users.setFullName("Super Admin");
        users.setRole(Roles.SUPERADMIN.name());
        users.setCreatedOn(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy, hh:mm:ss a")));
        userDetailsRepository.save(users);
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "super admin created successfully!");
        return response;
    }

    /***
     * method to user the super admin is available in the database
     * @implNote this method is void and it will throw error if the user admin already available
     */
    private void verifySuperAdminIsAlreadyAvailable() {
        List<Users> users = userDetailsRepository.getByRole("SUPERADMIN");
        if (users.size() > 0)
            throw new ThrowApiError("already one user admin available!", 1010, HttpStatus.ALREADY_REPORTED);
    }

    /***
     * additional add condition for Username  and if have number in username ,return error
     * @param body
     */
    private void verifySuperAdminBody(SuperAdminRequestBody body) {
        if (body.getUsername().length() < 3 || !body.getUsername().matches("^[a-zA-Z0-9_]*$"))
            throw new ThrowApiError("username can't be blank", 1012, HttpStatus.BAD_REQUEST);
        if (body.getPassword().isEmpty() || body.getPassword().length() < 5)
            throw new ThrowApiError("password can't be blank and less than 5 characters", 1013, HttpStatus.BAD_REQUEST);
        if (body.getUsername().matches("\\d+"))
            throw new ThrowApiError("username should alphanumeric values", 1013, HttpStatus.BAD_REQUEST);
    }

    /***
     * method to change the password of the super admin user
     * @param body
     * @return
     */
    public Map<String, Object> resetPassword(SuperAdminRequestBody body) {
        verifyResetPassword(body);
        Users users = findSuperAdmin(body.getUsername());
        validateResetPassword(body);
        users.setPassword(passwordConfig.passwordEncoder().encode(body.getNewpassword()));
        userDetailsRepository.save(users);
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "super admin password reset successfully!");
        return response;
    }
    private void verifyResetPassword(SuperAdminRequestBody body){

        if (body.getUsername().isEmpty()|| body.getUsername().length() < 3)
            throw new ThrowApiError("username can't be blank", 1012, HttpStatus.BAD_REQUEST);
        if (body.getNewpassword().isEmpty() || body.getNewpassword().length() < 5)
            throw new ThrowApiError("password can't be blank and less than 5 characters", 1013, HttpStatus.BAD_REQUEST);

    }

    private void validateResetPassword(SuperAdminRequestBody body) {
        if(body.getNewpassword().length()<5){
            throw new ThrowApiError("password can't be blank and less than 5 characters",1014,HttpStatus.BAD_REQUEST);
        }
    }

    /***
     * method to validate the username of the superadmin from api call
     * @param username
     * @return
     */
    private Users findSuperAdmin(String username) {
        List<Users> users = userDetailsRepository.findByEmail(username);
        if (users.size() == 0)
            throw new ThrowApiError("invalid super admin username", 1011, HttpStatus.NOT_FOUND);
        else
            return users.get(0);
    }

    /***
     * method to get all the user details from user_details_management table expect super admin details
     * @return
     */
    public GetAllUsersResponse getAllUsers() {
        List<UserDetailsWithoutPassword> users = userDetailsRepository.findAllByRoleNot(Roles.SUPERADMIN.name());
        GetAllUsersResponse response = new GetAllUsersResponse();
        GetAllUsersResponse.MetaData metaData = new GetAllUsersResponse.MetaData();
        if (users.size() == 0) {
            metaData.setMessage("no users found");
            metaData.setUserCount(0);
            metaData.setStatusCode(1041);
            response.setMetaData(metaData);
        } else {
            metaData.setMessage("users found");
            metaData.setUserCount(users.size());
            metaData.setStatusCode(1040);
            response.setMetaData(metaData);
        }
        response.setUsersList(users);
        return response;
    }

    public GetAllProjectsResponse getAllProjects() {
        String loggedInId=tokenDetails.getUserId();
        List<Project> projects = projectRepository.findAll();

        GetAllProjectsResponse.MetaData metaData = new GetAllProjectsResponse.MetaData();
        metaData.setProjectCount(projects.size());
        metaData.setMessage((projects.size() == 0) ? "projects not available" : "projects available");

        List<GetAllProjectsResponse.ProjectList> projectLists = new ArrayList<>();

        projects.forEach(project -> {
            GetAllProjectsResponse.ProjectList.ProjectDetails projectDetails = new GetAllProjectsResponse.ProjectList.ProjectDetails();
            projectDetails.setProjectId(project.getProjectId());
            projectDetails.setProjectName(project.getProjectName());

            GetAllProjectsResponse.ProjectList.OwnerDetails ownerDetails = new GetAllProjectsResponse.ProjectList.OwnerDetails();
            Users user= userDetailsRepository.findById(project.getOwner()).get();
            ownerDetails.setOwnerName((loggedInId.equals(project.getOwner()))?"Self":user.getFullName());
            ownerDetails.setOwnerId(project.getOwner());

            GetAllProjectsResponse.ProjectList projectList = new GetAllProjectsResponse.ProjectList();
            projectList.setProjectDetails(projectDetails);
            projectList.setOwnerDetails(ownerDetails);
            projectLists.add(projectList);
        });

        GetAllProjectsResponse response = new GetAllProjectsResponse();
        response.setProjectList(projectLists);
        response.setMetaData(metaData);
        return response;
    }
}

