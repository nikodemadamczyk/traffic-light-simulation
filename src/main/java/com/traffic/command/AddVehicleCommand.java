package com.traffic.command;

import com.traffic.model.Direction;
import com.traffic.model.VehicleType;
import com.traffic.simulation.SimulationContext;

public class AddVehicleCommand implements Command {
    private final String vehicleId;
    private final Direction startRoad;
    private final Direction endRoad;
    private final VehicleType vehicleType;

    public AddVehicleCommand(String vehicleId, String startRoad, String endRoad) {
        this(vehicleId, startRoad, endRoad, "car");
    }

    public AddVehicleCommand(String vehicleId, String startRoad, String endRoad, String vehicleType) {
        if (startRoad == null || endRoad == null) {
            throw new IllegalArgumentException("Start road and end road cannot be null");
        }

        this.vehicleId = vehicleId;
        this.startRoad = Direction.fromString(startRoad);
        this.endRoad = Direction.fromString(endRoad);
        this.vehicleType = VehicleType.fromString(vehicleType);
    }

    @Override
    public void execute(SimulationContext context) {
        context.addVehicle(vehicleId, startRoad, endRoad, vehicleType);

        String priorityInfo = vehicleType.hasPriority() ? " [EMERGENCY PRIORITY]" : "";
        System.out.println("Added " + vehicleType.getJsonName() + ": " + vehicleId +
                " from " + startRoad + " to " + endRoad + priorityInfo);
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

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}