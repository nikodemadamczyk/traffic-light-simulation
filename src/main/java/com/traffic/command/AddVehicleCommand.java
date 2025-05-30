package com.traffic.command;

import com.traffic.model.Direction;
import com.traffic.simulation.SimulationContext;

public class AddVehicleCommand implements Command {
    private final String vehicleId;
    private final Direction startRoad;
    private final Direction endRoad;

    public AddVehicleCommand(String vehicleId, String startRoad, String endRoad) {
        this.vehicleId = vehicleId;
        this.startRoad = Direction.fromString(startRoad);
        this.endRoad = Direction.fromString(endRoad);
    }

    @Override
    public void execute(SimulationContext context) {
        context.addVehicle(vehicleId, startRoad, endRoad);
        System.out.println("Added vehicle: " + vehicleId + " from " + startRoad + " to " + endRoad);
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
}