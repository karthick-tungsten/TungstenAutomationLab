package com.tungstenautomationlab.tungstenautomationlab.modules.project;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ProjectDetails")
public class Project {

    @Id
    private String ProjectId;
    private String ProjectName;
    private String Owner;

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String projectId) {
        ProjectId = projectId;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }
}


