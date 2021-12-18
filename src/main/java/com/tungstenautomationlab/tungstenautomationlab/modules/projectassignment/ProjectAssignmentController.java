package com.tungstenautomationlab.tungstenautomationlab.modules.projectassignment;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/projectAssignment")
@AllArgsConstructor
public class ProjectAssignmentController {

    private final ProjectAssignmentService projectAssignmentService;

    @PostMapping(path = "assign")
    public Map<String, Object> assignProject(@RequestBody Map<String,Object> body){
       return projectAssignmentService.asssignProject(body);
    }
}
