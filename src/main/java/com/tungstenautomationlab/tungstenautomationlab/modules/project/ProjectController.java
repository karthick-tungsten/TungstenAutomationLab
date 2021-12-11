package com.tungstenautomationlab.tungstenautomationlab.modules.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    @GetMapping("api/v1/getProjectDetails")
    public Map<String, List<Map<String, String>>> getProjectDetails() {
        Map<String, List<Map<String, String>>> map=new HashMap<>();
        map.put("projectDetails",projectService.getProjectDetails());
        return map;
    }
    @PutMapping("/api/v1/updateProject")
    public Map<String,Object> updateProject(@RequestBody Map<String,String> updatepro){
       return projectService.updateproject(updatepro);



    }



}
