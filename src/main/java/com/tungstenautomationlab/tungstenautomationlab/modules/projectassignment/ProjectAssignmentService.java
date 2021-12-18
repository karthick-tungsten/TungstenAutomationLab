package com.tungstenautomationlab.tungstenautomationlab.modules.projectassignment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProjectAssignmentService {
    private final ProjectAssignmentRepository assignmentRepository;

    public Map<String, Object> asssignProject(Map<String, Object> body) {
        ProjectAssignment projectAssignment = new ProjectAssignment();
        projectAssignment.setProjectId(body.get("projectId").toString());
        projectAssignment.setAssignedUser(body.get("assignedUser").toString());
        projectAssignment.setIsDefault((boolean) body.get("isDefault"));
        projectAssignment.setCreatedOn(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy, hh:mm:ss a")));
        assignmentRepository.save(projectAssignment);
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "project assigned successfully");
        return response;
    }
}
