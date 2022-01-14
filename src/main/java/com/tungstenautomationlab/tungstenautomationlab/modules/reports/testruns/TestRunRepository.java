package com.tungstenautomationlab.tungstenautomationlab.modules.reports.testruns;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRunRepository extends JpaRepository<TestRun,String> {
    List<TestRun> findAllByName(String name);
}
