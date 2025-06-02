package com.traffic.model;

public enum VehicleType {
    CAR("car", 1, false),
    BUS("bus", 2, false),
    EMERGENCY("emergency", 1, true),
    PEDESTRIAN("pedestrian", 1, false);

    private final String jsonName;
    private final int processingTime;
    private final boolean hasPriority;

    VehicleType(String jsonName, int processingTime, boolean hasPriority) {
        this.jsonName = jsonName;
        this.processingTime = processingTime;
        this.hasPriority = hasPriority;
    }

    public String getJsonName() {
        return jsonName;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public boolean hasPriority() {
        return hasPriority;
    }

    public static VehicleType fromString(String type) {
        if (type == null || type.trim().isEmpty()) {
            return CAR;
        }

        for (VehicleType vehicleType : VehicleType.values()) {
            if (vehicleType.jsonName.equalsIgnoreCase(type.trim())) {
                return vehicleType;
            }
        }
        return CAR;
    }
}