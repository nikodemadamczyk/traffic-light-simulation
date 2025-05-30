package com.traffic.simulation;

import com.traffic.controller.TrafficLightController;
import com.traffic.model.*;

import java.util.ArrayList;
import java.util.List;

public class SimulationContext {
    private final Intersection intersection;
    private final TrafficLightController controller;
    private final List<List<String>> stepResults;

    public SimulationContext() {
        this.intersection = new Intersection();
        this.controller = new TrafficLightController(intersection);
        this.stepResults = new ArrayList<>();
    }

    public void addVehicle(String vehicleId, Direction startRoad, Direction endRoad) {
        Vehicle vehicle = new Vehicle(vehicleId, startRoad, endRoad);
        intersection.addVehicle(vehicle);
    }

    public void executeStep() {
        controller.update();

        List<String> leftVehicles = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            if (controller.canVehiclesPass(direction)) {
                List<Vehicle> processedVehicles = intersection.processVehiclesFromRoad(direction, 5);
                for (Vehicle vehicle : processedVehicles) {
                    leftVehicles.add(vehicle.getVehicleId());
                }
            }
        }

        stepResults.add(leftVehicles);
    }

    public Intersection getIntersection() {
        return intersection;
    }

    public TrafficLightController getController() {
        return controller;
    }

    public List<List<String>> getStepResults() {
        return stepResults;
    }

    public void printCurrentState() {
        System.out.println("=== Current State ===");
        System.out.println("Phase: " + controller.getCurrentPhase() + " (" + controller.getPhaseState() + ")");
        System.out.println("Waiting vehicles: N=" + intersection.getWaitingVehiclesCount(Direction.NORTH) +
                ", S=" + intersection.getWaitingVehiclesCount(Direction.SOUTH) +
                ", E=" + intersection.getWaitingVehiclesCount(Direction.EAST) +
                ", W=" + intersection.getWaitingVehiclesCount(Direction.WEST));
    }
}