package com.tungstenautomationlab.tungstenautomationlab.modules.reports.testruns.responsebody;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreatedTestRunResponse {
    private String id;
    private String message;
}
