package com.tungstenautomationlab.tungstenautomationlab.modules.reports.testruns;

import com.tungstenautomationlab.tungstenautomationlab.modules.reports.testruns.requestbody.CreateTestRunRequest;
import com.tungstenautomationlab.tungstenautomationlab.modules.reports.testruns.responsebody.CreatedTestRunResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/testruns")
@AllArgsConstructor
public class TestRunController {
    private final TestRunService testRunService;

    @PostMapping(path = "create")
    public CreatedTestRunResponse createTestRun(@RequestBody CreateTestRunRequest body){
        return testRunService.createTestRun(body);
    }
}
