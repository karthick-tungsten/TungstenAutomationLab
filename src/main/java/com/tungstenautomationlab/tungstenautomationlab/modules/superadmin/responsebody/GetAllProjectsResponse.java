package com.tungstenautomationlab.tungstenautomationlab.modules.superadmin.responsebody;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class GetAllProjectsResponse {
    private MetaData metaData;
    private List<ProjectList> projectList;

    @Getter
    @Setter
    public static class MetaData {
        private int projectCount;
        private String message;
    }
    @Setter
    @Getter
    public static class ProjectList {
        private ProjectDetails projectDetails;
        private OwnerDetails ownerDetails;
        @Setter
        @Getter
        public static class ProjectDetails {
            private String projectId;
            private String projectName;
        }
        @Setter
        @Getter
        public static class OwnerDetails{
            private String  ownerId;
            private String ownerName;
        }
    }

}
