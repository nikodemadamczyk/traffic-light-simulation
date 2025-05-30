package com.traffic.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Road {
    private final Direction direction;
    private final Queue<Vehicle> waitingVehicles;
    private int totalVehiclesCount;

    public Road(Direction direction) {
        this.direction = direction;
        this.waitingVehicles = new LinkedList<>();
        this.totalVehiclesCount = 0;
    }

    public void addVehicle(Vehicle vehicle) {
        if (vehicle.getStartRoad() != this.direction) {
            throw new IllegalArgumentException("Vehicle start road " + vehicle.getStartRoad() +
                    " does not match road direction " + this.direction);
        }
        waitingVehicles.offer(vehicle);
        totalVehiclesCount++;
    }

    public List<Vehicle> removeVehicles(int count) {
        List<Vehicle> removedVehicles = new ArrayList<>();
        for (int i = 0; i < count && !waitingVehicles.isEmpty(); i++) {
            removedVehicles.add(waitingVehicles.poll());
        }
        return removedVehicles;
    }

    public Vehicle peekNextVehicle() {
        return waitingVehicles.peek();
    }

    public int getWaitingVehiclesCount() {
        return waitingVehicles.size();
    }

    public int getTotalVehiclesCount() {
        return totalVehiclesCount;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean hasWaitingVehicles() {
        return !waitingVehicles.isEmpty();
    }

    public List<Vehicle> getWaitingVehiclesList() {
        return new ArrayList<>(waitingVehicles);
    }

    @Override
    public String toString() {
        return "Road{" +
                "direction=" + direction +
                ", waiting=" + waitingVehicles.size() +
                ", total=" + totalVehiclesCount +
                '}';
    }
}