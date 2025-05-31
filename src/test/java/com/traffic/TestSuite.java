package com.traffic;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({
        "com.traffic.model",
        "com.traffic.command",
        "com.traffic.json",
        "com.traffic.simulation",
        "com.traffic.validation"
})
public class TestSuite {
}
