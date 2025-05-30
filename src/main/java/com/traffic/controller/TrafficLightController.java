package com.traffic.controller;

import com.traffic.model.*;

import java.util.EnumMap;
import java.util.Map;

public class TrafficLightController {
    private static final int MIN_GREEN_TIME = 10;
    private static final int MAX_GREEN_TIME = 60;
    private static final int YELLOW_TIME = 3;
    private static final int ALL_RED_TIME = 2;

    private final Map<Direction, TrafficLight> trafficLights;
    private final Intersection intersection;
    private TrafficPhase currentPhase;
    private PhaseState phaseState;
    private int phaseTimer;

    public TrafficLightController(Intersection intersection) {
        this.intersection = intersection;
        this.trafficLights = new EnumMap<>(Direction.class);
        this.currentPhase = TrafficPhase.NORTH_SOUTH;
        this.phaseState = PhaseState.GREEN;
        this.phaseTimer = 0;

        initializeTrafficLights();
        startPhase();
    }

    private void initializeTrafficLights() {
        for (Direction direction : Direction.values()) {
            trafficLights.put(direction, new TrafficLight(direction));
        }
    }

    public void update() {
        decrementTimers();

        if (isPhaseTransitionNeeded()) {
            transitionToNextPhase();
        }
    }

    private void decrementTimers() {
        phaseTimer--;
        for (TrafficLight light : trafficLights.values()) {
            light.decrementTime();
        }
    }

    private boolean isPhaseTransitionNeeded() {
        return phaseTimer <= 0;
    }

    private void transitionToNextPhase() {
        switch (phaseState) {
            case GREEN:
                setYellowPhase();
                break;
            case YELLOW:
                setAllRedPhase();
                break;
            case ALL_RED:
                switchToNextPhase();
                break;
        }
    }

    private void setYellowPhase() {
        phaseState = PhaseState.YELLOW;
        phaseTimer = YELLOW_TIME;

        for (Direction direction : currentPhase.getAllowedDirections()) {
            trafficLights.get(direction).setState(TrafficLightState.YELLOW, YELLOW_TIME);
        }
    }

    private void setAllRedPhase() {
        phaseState = PhaseState.ALL_RED;
        phaseTimer = ALL_RED_TIME;

        for (TrafficLight light : trafficLights.values()) {
            light.setState(TrafficLightState.RED, ALL_RED_TIME);
        }
    }

    private void switchToNextPhase() {
        currentPhase = currentPhase.getNext();
        startPhase();
    }

    private void startPhase() {
        phaseState = PhaseState.GREEN;
        int greenTime = calculateGreenTime();
        phaseTimer = greenTime;

        for (Direction direction : Direction.values()) {
            if (currentPhase.isDirectionAllowed(direction)) {
                trafficLights.get(direction).setState(TrafficLightState.GREEN, greenTime);
            } else {
                trafficLights.get(direction).setState(TrafficLightState.RED, greenTime);
            }
        }
    }

    private int calculateGreenTime() {
        int totalWaiting = 0;
        int phaseWaiting = 0;

        for (Direction direction : Direction.values()) {
            int waiting = intersection.getWaitingVehiclesCount(direction);
            totalWaiting += waiting;

            if (currentPhase.isDirectionAllowed(direction)) {
                phaseWaiting += waiting;
            }
        }

        if (totalWaiting == 0) {
            return MIN_GREEN_TIME;
        }

        double ratio = (double) phaseWaiting / totalWaiting;
        int adaptiveTime = (int) (MIN_GREEN_TIME + ratio * (MAX_GREEN_TIME - MIN_GREEN_TIME));

        return Math.max(MIN_GREEN_TIME, Math.min(MAX_GREEN_TIME, adaptiveTime));
    }

    public boolean canVehiclesPass(Direction direction) {
        return trafficLights.get(direction).canVehiclesPass();
    }

    public TrafficLight getTrafficLight(Direction direction) {
        return trafficLights.get(direction);
    }

    public TrafficPhase getCurrentPhase() {
        return currentPhase;
    }

    public PhaseState getPhaseState() {
        return phaseState;
    }

    public Map<Direction, TrafficLight> getAllTrafficLights() {
        return trafficLights;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TrafficLightController{\n");
        sb.append("  Phase: ").append(currentPhase).append(" (").append(phaseState).append(")\n");
        sb.append("  Timer: ").append(phaseTimer).append("\n");
        for (Map.Entry<Direction, TrafficLight> entry : trafficLights.entrySet()) {
            sb.append("  ").append(entry.getValue()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}