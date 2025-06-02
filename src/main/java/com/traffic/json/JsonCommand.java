package com.traffic.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonCommand {
    @JsonProperty("type")
    private String type;

    @JsonProperty("vehicleId")
    private String vehicleId;

    @JsonProperty("startRoad")
    private String startRoad;

    @JsonProperty("endRoad")
    private String endRoad;

    @JsonProperty("vehicleType")
    private String vehicleType;

    @JsonProperty("trafficMode")
    private String trafficMode;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getStartRoad() {
        return startRoad;
    }

    public void setStartRoad(String startRoad) {
        this.startRoad = startRoad;
    }

    public String getEndRoad() {
        return endRoad;
    }

    public void setEndRoad(String endRoad) {
        this.endRoad = endRoad;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getTrafficMode() {
        return trafficMode;
    }

    public void setTrafficMode(String trafficMode) {
        this.trafficMode = trafficMode;
    }
}