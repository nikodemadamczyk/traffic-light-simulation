package com.traffic.model;

import java.util.Objects;

public class Vehicle {
    private final String vehicleId;
    private final Direction startRoad;
    private final Direction endRoad;
    private final TurnType turnType;
    private final VehicleType vehicleType;

    public Vehicle(String vehicleId, Direction startRoad, Direction endRoad) {
        this(vehicleId, startRoad, endRoad, VehicleType.CAR);
    }

    public Vehicle(String vehicleId, Direction startRoad, Direction endRoad, VehicleType vehicleType) {
        if (vehicleId == null) {
            throw new NullPointerException("Vehicle ID cannot be null");
        }
        if (startRoad == null) {
            throw new NullPointerException("Start road cannot be null");
        }
        if (endRoad == null) {
            throw new NullPointerException("End road cannot be null");
        }
        if (vehicleType == null) {
            throw new NullPointerException("Vehicle type cannot be null");
        }

        this.vehicleId = vehicleId;
        this.startRoad = startRoad;
        this.endRoad = endRoad;
        this.vehicleType = vehicleType;
        this.turnType = determineTurnType(startRoad, endRoad);
    }

    private TurnType determineTurnType(Direction start, Direction end) {
        if (start == end.getOpposite()) {
            return TurnType.STRAIGHT;
        } else if (start.isPerpendicularTo(end)) {
            return TurnType.TURN;
        } else {
            throw new IllegalArgumentException("Invalid vehicle route from " + start + " to " + end);
        }
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public Direction getStartRoad() {
        return startRoad;
    }

    public Direction getEndRoad() {
        return endRoad;
    }

    public TurnType getTurnType() {
        return turnType;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public boolean hasEmergencyPriority() {
        return vehicleType.hasPriority();
    }

    public int getProcessingTime() {
        return vehicleType.getProcessingTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(vehicleId, vehicle.vehicleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + vehicleId + '\'' +
                ", type=" + vehicleType +
                ", from=" + startRoad +
                ", to=" + endRoad +
                ", turn=" + turnType +
                '}';
    }
}