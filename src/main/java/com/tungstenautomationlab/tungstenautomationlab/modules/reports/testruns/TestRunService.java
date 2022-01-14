package com.tungstenautomationlab.tungstenautomationlab.modules.reports.testruns;

import com.tungstenautomationlab.tungstenautomationlab.modules.reports.testruns.constants.TestRunStatus;
import com.tungstenautomationlab.tungstenautomationlab.modules.reports.testruns.requestbody.CreateTestRunRequest;
import com.tungstenautomationlab.tungstenautomationlab.modules.reports.testruns.responsebody.CreatedTestRunResponse;
import com.tungstenautomationlab.tungstenautomationlab.supports.reusable.SupportFunctions;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TestRunService {
    private final SupportFunctions supportFunctions;
    private final TestRunRepository testRunRepository;

    public CreatedTestRunResponse createTestRun(CreateTestRunRequest body) {
        boolean createNew = true;
        if (body.isRerun()) {
            createNew = rerunLogic(body.getName());
        }
        if (createNew) {
            TestRun obj = new TestRun();
            String id = UUID.randomUUID().toString();
            obj.setId(id);
            obj.setName(body.getName());
            obj.setCount(countLogic(body.getName()));
            obj.setDescription(body.getDescription());
            obj.setCreateOn(supportFunctions.getCurrentDateWithDefaultFormat());
            obj.setStatus(TestRunStatus.IN_PROGRESS);
            obj.setTags(body.getTags());
            testRunRepository.save(obj);
            return new CreatedTestRunResponse(id, "test run created successfully");
        } else {
            List<TestRun> listTestRuns = testRunRepository.findAllByName(body.getName());
            int number = listTestRuns.stream().mapToInt(TestRun::getCount).max().orElseThrow(NullPointerException::new);
            TestRun lastTestRun=listTestRuns.stream().filter((list)->list.getCount()==number).findFirst().get();
            if(lastTestRun.getStatus()!=TestRunStatus.IN_PROGRESS){
                lastTestRun.setStatus(TestRunStatus.IN_PROGRESS);
            }
            lastTestRun.setLastUpdate(supportFunctions.getCurrentDateWithDefaultFormat());
            testRunRepository.save(lastTestRun);
            return new CreatedTestRunResponse(lastTestRun.getId(),"reusing last launch");
        }
    }

    private boolean rerunLogic(String name) {
        List<TestRun> listTestRuns = testRunRepository.findAllByName(name);
        return listTestRuns.size() == 0;
    }

    private int countLogic(String name) {
        List<TestRun> listTestRuns = testRunRepository.findAllByName(name);
        return (listTestRuns.size() == 0) ? 1 : listTestRuns.size() + 1;
    }
}


