package com.tungstenautomationlab.tungstenautomationlab.modules.testresults.testrun;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "TestRun")
@Getter
@Setter
public class TestRun {
    @Id
    private String id;
    private String projectId;
    private Status status;
    private String[] tags;
    private int total;
    private int passed;
    private int failed;
    private Boolean rerun;
    private String createdOn;
    private String lastUpdate;

}
