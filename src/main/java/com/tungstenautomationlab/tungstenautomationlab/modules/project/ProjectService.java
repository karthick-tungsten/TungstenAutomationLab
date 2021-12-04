package com.tungstenautomationlab.tungstenautomationlab.modules.project;

import com.tungstenautomationlab.tungstenautomationlab.supports.security.TokenDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        projectRepository.save(project);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("message", "user project successfully!");
        return map;
    }
}
