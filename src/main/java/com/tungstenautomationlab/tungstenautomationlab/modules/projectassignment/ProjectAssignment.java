package com.tungstenautomationlab.tungstenautomationlab.modules.projectassignment;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "ProjectAssignment")
public class ProjectAssignment {
    @Id
    @GeneratedValue
    private long tableId;
    private String projectId;
    private String assignedUser;
    private String createdOn;
    private Boolean isDefault;
}
