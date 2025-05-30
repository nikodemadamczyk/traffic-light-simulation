package com.traffic.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JsonOutput {
    @JsonProperty("stepStatuses")
    private List<StepStatus> stepStatuses;

    public List<StepStatus> getStepStatuses() {
        return stepStatuses;
    }

    public void setStepStatuses(List<StepStatus> stepStatuses) {
        this.stepStatuses = stepStatuses;
    }
}