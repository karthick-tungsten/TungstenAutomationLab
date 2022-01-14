package com.tungstenautomationlab.tungstenautomationlab.modules.reports.testruns;

import com.tungstenautomationlab.tungstenautomationlab.modules.reports.testruns.constants.TestRunStatus;
import com.tungstenautomationlab.tungstenautomationlab.modules.reports.testruns.converters.TagsConverter;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity(name = "ReportTestRun")
@Getter
@Setter
public class TestRun {
    @Id
    private String id;
    private String name;
    private int count;
    @Nullable
    private String description;
    private TestRunStatus status;
    private int total;
    private int passed;
    private int failed;
    private int skipped;
    @ElementCollection
    private List<String> tags;
    private String createOn;
    private String lastUpdate;
}
