package com.tungstenautomationlab.tungstenautomationlab.modules.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Map<String, Object> createProject(Map<String, String> body) {
        String projectName = body.get("ProjectName");
        Project project = new Project();
        project.setProjectName(projectName);
        project.setProjectId(UUID.randomUUID().toString());
        project.setOwner("ownerXXX");
        projectRepository.save(project);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("message", "user project successfully!");
        return map;
    }
}
