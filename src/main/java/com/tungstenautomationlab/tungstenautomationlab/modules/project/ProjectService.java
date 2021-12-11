package com.tungstenautomationlab.tungstenautomationlab.modules.project;

import com.tungstenautomationlab.tungstenautomationlab.supports.expection.ThrowApiError;
import com.tungstenautomationlab.tungstenautomationlab.supports.security.TokenDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TokenDetails tokenDetails;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, TokenDetails tokenDetails) {
        this.projectRepository = projectRepository;
        this.tokenDetails = tokenDetails;
    }


    public Map<String, Object> createProject(Map<String, String> body) {
        String projectName = body.get("projectName");
        Project project = new Project();
        project.setProjectName(projectName);
        project.setProjectId(UUID.randomUUID().toString());
        project.setOwner(tokenDetails.getUserId());
        project.setCreatedOn(LocalDateTime.now().toString());
        projectRepository.save(project);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("message", "user project successfully!");
        return map;
    }

    public List<Map<String, String>> getProjectDetails() {
        String userId = tokenDetails.getUserId();
        List<Project> project = projectRepository.findByOwner(userId);
        List<Map<String, String>> out = new ArrayList<>();
        project.forEach((proj -> {
            Map<String, String> map = new HashMap<>();
            map.put("projectId", proj.getProjectId());
            map.put("projectName", proj.getProjectName());
            map.put("createdOn", proj.getCreatedOn());
            map.put("lastUpdate", proj.getLastUpdate());
            out.add(map);
        }));
        return out;
    }


    public Map<String, Object> updateproject(Map<String, String> updatepro) {
        validateUpdateProjectRequestBody(updatepro);
        Project project = projectRepository.findById(updatepro.get("projectId")).get();
        project.setProjectName(updatepro.get("updateprojectName"));
        projectRepository.save(project);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("message", "update project successfully!");
        return map;
    }

    private void validateUpdateProjectRequestBody(Map<String, String> updatepro) {
        if (updatepro.get("projectId").equals("")){
            throw new ThrowApiError("projectid cannot be empty",1030, HttpStatus.BAD_REQUEST);
        }
        if (updatepro.get("updateprojectName").equals("")){
            throw new ThrowApiError("projectname cannot be empty",1031, HttpStatus.BAD_REQUEST);
        }
       Optional<Project> project = projectRepository.findById(updatepro.get("projectId"));
        if(!project.isPresent()){
            throw new ThrowApiError("project cannot found",1032,HttpStatus.BAD_REQUEST);
        }
    }
}
