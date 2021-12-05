package com.tungstenautomationlab.tungstenautomationlab.modules.project;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository <Project,String>{
    List<Project> findByOwner(String ownerId);
}
