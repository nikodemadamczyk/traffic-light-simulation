package com.traffic.model;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Intersection {
    private final Map<Direction, Road> roads;

    public Intersection() {
        this.roads = new EnumMap<>(Direction.class);
        for (Direction direction : Direction.values()) {
            roads.put(direction, new Road(direction));
        }
    }

    public void addVehicle(Vehicle vehicle) {
        Road startRoad = roads.get(vehicle.getStartRoad());
        startRoad.addVehicle(vehicle);
    }

    public Road getRoad(Direction direction) {
        return roads.get(direction);
    }

    public List<Vehicle> processVehiclesFromRoad(Direction direction, int maxVehicles) {
        Road road = roads.get(direction);
        return road.removeVehicles(maxVehicles);
    }

    public int getTotalWaitingVehicles() {
        return roads.values().stream()
                .mapToInt(Road::getWaitingVehiclesCount)
                .sum();
    }

    public int getWaitingVehiclesCount(Direction direction) {
        return roads.get(direction).getWaitingVehiclesCount();
    }

    public boolean hasWaitingVehicles(Direction direction) {
        return roads.get(direction).hasWaitingVehicles();
    }

    public Map<Direction, Road> getAllRoads() {
        return roads;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Intersection{\n");
        for (Map.Entry<Direction, Road> entry : roads.entrySet()) {
            sb.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}