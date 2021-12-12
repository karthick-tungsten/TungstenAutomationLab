package com.tungstenautomationlab.tungstenautomationlab.modules.superadmin.responsebody;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetAllUsersResponse {

    private MetaData metaData;
    private List<UserDetailsWithoutPassword> usersList;

    @Getter
    @Setter
    public static class MetaData{
        private int statusCode;
        private int userCount;
        private String message;
    }
}
