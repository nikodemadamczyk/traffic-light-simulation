package com.traffic.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class StepStatus {
    @JsonProperty("leftVehicles")
    private List<String> leftVehicles;

    public StepStatus(List<String> leftVehicles) {
        this.leftVehicles = leftVehicles;
    }

    public StepStatus() {
    }

    public List<String> getLeftVehicles() {
        return leftVehicles;
    }

    public void setLeftVehicles(List<String> leftVehicles) {
        this.leftVehicles = leftVehicles;
    }
}