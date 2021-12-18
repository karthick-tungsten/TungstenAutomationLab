package com.tungstenautomationlab.tungstenautomationlab.modules.projectassignment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectAssignmentRepository extends JpaRepository <ProjectAssignment,String>{

    List<ProjectAssignment> findByAssignedUser(String userId);
}
