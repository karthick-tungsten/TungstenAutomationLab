package com.tungstenautomationlab.tungstenautomationlab.modules.project;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;

@Entity(name = "ProjectDetails")
@Getter
@Setter
public class Project  {

    @Id
    private String projectId;
    private String projectName;
    private String owner;
    private String createdOn;
    @Nullable
    private String lastUpdate;

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



