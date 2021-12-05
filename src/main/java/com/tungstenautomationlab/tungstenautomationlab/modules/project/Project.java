package com.tungstenautomationlab.tungstenautomationlab.modules.project;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ProjectDetails")
public class Project {

    @Id
    private String projectId;
    private String projectName;
    private String owner;
    private String createdOn;
    @Nullable
    private String lastUpdate;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @Nullable
    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(@Nullable String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", owner='" + owner + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                '}';
    }
}



