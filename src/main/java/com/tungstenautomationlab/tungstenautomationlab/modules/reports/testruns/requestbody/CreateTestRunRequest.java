package com.tungstenautomationlab.tungstenautomationlab.modules.reports.testruns.requestbody;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CreateTestRunRequest {
    private String name;
    private String description;
    private boolean rerun;
    private List<String> tags;
}
