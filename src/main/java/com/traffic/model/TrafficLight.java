package com.traffic.model;

public class TrafficLight {
    private final Direction direction;
    private TrafficLightState state;
    private int remainingTime;

    public TrafficLight(Direction direction) {
        this.direction = direction;
        this.state = TrafficLightState.RED;
        this.remainingTime = 0;
    }

    public void setState(TrafficLightState state, int duration) {
        this.state = state;
        this.remainingTime = duration;
    }

    public void decrementTime() {
        if (remainingTime > 0) {
            remainingTime--;
        }
    }

    public boolean isTimeExpired() {
        return remainingTime <= 0;
    }

    public boolean canVehiclesPass() {
        return state == TrafficLightState.GREEN;
    }

    public Direction getDirection() {
        return direction;
    }

    public TrafficLightState getState() {
        return state;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    @Override
    public String toString() {
        return "TrafficLight{" +
                "direction=" + direction +
                ", state=" + state +
                ", remaining=" + remainingTime +
                '}';
    }
}
