package com.tungstenautomationlab.tungstenautomationlab.modules.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("api/v1/createProject")
    @PreAuthorize("hasAnyRole('SUPERADMIN','MANAGER')")
    public Map<String, Object> createProject(@RequestBody Map<String, String> body) {
       return projectService.createProject(body);

    }
}
