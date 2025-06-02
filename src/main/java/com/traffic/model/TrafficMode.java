package com.traffic.model;

public enum TrafficMode {
    NORMAL("normal", 1.0, 10, 60),
    RUSH_HOUR("rush_hour", 0.7, 15, 45),
    NIGHT("night", 1.5, 5, 30),
    EMERGENCY("emergency", 0.5, 20, 80);

    private final String jsonName;
    private final double timingMultiplier;
    private final int minGreenTime;
    private final int maxGreenTime;

    TrafficMode(String jsonName, double timingMultiplier, int minGreenTime, int maxGreenTime) {
        this.jsonName = jsonName;
        this.timingMultiplier = timingMultiplier;
        this.minGreenTime = minGreenTime;
        this.maxGreenTime = maxGreenTime;
    }

    public String getJsonName() {
        return jsonName;
    }

    public double getTimingMultiplier() {
        return timingMultiplier;
    }

    public int getMinGreenTime() {
        return minGreenTime;
    }

    public int getMaxGreenTime() {
        return maxGreenTime;
    }

    public static TrafficMode fromString(String mode) {
        if (mode == null || mode.trim().isEmpty()) {
            return NORMAL;
        }

        for (TrafficMode trafficMode : TrafficMode.values()) {
            if (trafficMode.jsonName.equalsIgnoreCase(mode.trim())) {
                return trafficMode;
            }
        }
        return NORMAL;
    }
}