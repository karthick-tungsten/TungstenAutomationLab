package com.tungstenautomationlab.tungstenautomationlab.modules.testresults.testrun;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/testRun")
@AllArgsConstructor
public class TestRunController {
    @PostMapping(path = "create")
    public void createTestRun() {

    }
}

