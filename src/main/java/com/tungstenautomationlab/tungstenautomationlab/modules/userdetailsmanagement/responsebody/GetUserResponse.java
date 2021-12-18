package com.tungstenautomationlab.tungstenautomationlab.modules.userdetailsmanagement.responsebody;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetUserResponse {

    private String fullName;
    private String email;
    private String role;
    private String lastUpdate;
    private String createdOn;
    private List<ProjectDetails> projectDetails;

    @Getter
    @Setter
    public static class ProjectDetails {
        private String projectId;
        private String projectName;
        private boolean isDefault;
    }
}
