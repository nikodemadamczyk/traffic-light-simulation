package com.traffic.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Road {
    private final Direction direction;
    private final Queue<Vehicle> waitingVehicles;
    private final Queue<Vehicle> priorityVehicles;
    private int totalVehiclesCount;

    public Road(Direction direction) {
        this.direction = direction;
        this.waitingVehicles = new LinkedList<>();
        this.priorityVehicles = new LinkedList<>();
        this.totalVehiclesCount = 0;
    }

    public void addVehicle(Vehicle vehicle) {
        if (vehicle.getStartRoad() != this.direction) {
            throw new IllegalArgumentException("Vehicle start road " + vehicle.getStartRoad() +
                    " does not match road direction " + this.direction);
        }

        if (vehicle.hasEmergencyPriority()) {
            priorityVehicles.offer(vehicle);
        } else {
            waitingVehicles.offer(vehicle);
        }
        totalVehiclesCount++;
    }

    public List<Vehicle> removeVehicles(int maxCount) {
        List<Vehicle> removedVehicles = new ArrayList<>();
        int processedCount = 0;

        // Process priority vehicles first (emergency vehicles)
        while (processedCount < maxCount && !priorityVehicles.isEmpty()) {
            Vehicle priorityVehicle = priorityVehicles.poll();
            removedVehicles.add(priorityVehicle);
            processedCount += priorityVehicle.getProcessingTime();
        }

        // Then process regular vehicles
        while (processedCount < maxCount && !waitingVehicles.isEmpty()) {
            Vehicle vehicle = waitingVehicles.peek();
            if (processedCount + vehicle.getProcessingTime() <= maxCount) {
                waitingVehicles.poll();
                removedVehicles.add(vehicle);
                processedCount += vehicle.getProcessingTime();
            } else {
                break; // Can't fit this vehicle in remaining capacity
            }
        }

        return removedVehicles;
    }

    public Vehicle peekNextVehicle() {
        if (!priorityVehicles.isEmpty()) {
            return priorityVehicles.peek();
        }
        return waitingVehicles.peek();
    }

    public int getWaitingVehiclesCount() {
        return waitingVehicles.size() + priorityVehicles.size();
    }

    public int getPriorityVehiclesCount() {
        return priorityVehicles.size();
    }

    public boolean hasEmergencyVehicles() {
        return !priorityVehicles.isEmpty();
    }

    public int getTotalVehiclesCount() {
        return totalVehiclesCount;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean hasWaitingVehicles() {
        return !waitingVehicles.isEmpty() || !priorityVehicles.isEmpty();
    }

    public List<Vehicle> getWaitingVehiclesList() {
        List<Vehicle> allVehicles = new ArrayList<>(priorityVehicles);
        allVehicles.addAll(waitingVehicles);
        return allVehicles;
    }

    public int calculateWeight() {
        int weight = 0;

        // Emergency vehicles get very high weight
        weight += priorityVehicles.size() * 10;

        // Regular vehicles
        weight += waitingVehicles.stream()
                .mapToInt(v -> {
                    int baseWeight = 1;
                    if (v.getVehicleType() == VehicleType.BUS) {
                        baseWeight = 2; // Buses count as 2 regular vehicles
                    }
                    return baseWeight;
                })
                .sum();

        return weight;
    }

    @Override
    public String toString() {
        return "Road{" +
                "direction=" + direction +
                ", waiting=" + waitingVehicles.size() +
                ", priority=" + priorityVehicles.size() +
                ", total=" + totalVehiclesCount +
                '}';
    }
}