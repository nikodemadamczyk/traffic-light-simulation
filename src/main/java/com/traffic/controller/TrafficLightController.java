package com.traffic.controller;

import com.traffic.model.*;

import java.util.EnumMap;
import java.util.Map;

public class TrafficLightController {
    private static final int DEFAULT_MIN_GREEN_TIME = 10;
    private static final int DEFAULT_MAX_GREEN_TIME = 60;
    private static final int YELLOW_TIME = 3;
    private static final int ALL_RED_TIME = 2;
    private static final int EMERGENCY_GREEN_TIME = 15;

    private final Map<Direction, TrafficLight> trafficLights;
    private final Intersection intersection;
    private TrafficPhase currentPhase;
    private PhaseState phaseState;
    private TrafficMode trafficMode;
    private int phaseTimer;
    private boolean emergencyMode;
    private Direction emergencyDirection;

    public TrafficLightController(Intersection intersection) {
        this(intersection, TrafficMode.NORMAL);
    }

    public TrafficLightController(Intersection intersection, TrafficMode trafficMode) {
        this.intersection = intersection;
        this.trafficLights = new EnumMap<>(Direction.class);
        this.currentPhase = TrafficPhase.NORTH_SOUTH;
        this.phaseState = PhaseState.GREEN;
        this.trafficMode = trafficMode;
        this.phaseTimer = 0;
        this.emergencyMode = false;
        this.emergencyDirection = null;

        initializeTrafficLights();
        startPhase();
    }

    private void initializeTrafficLights() {
        for (Direction direction : Direction.values()) {
            trafficLights.put(direction, new TrafficLight(direction));
        }
    }

    public void update() {
        checkForEmergencyVehicles();

        decrementTimers();

        if (isPhaseTransitionNeeded()) {
            transitionToNextPhase();
        }
    }

    private void checkForEmergencyVehicles() {
        for (Direction direction : Direction.values()) {
            Road road = intersection.getRoad(direction);
            if (road.hasEmergencyVehicles() && !emergencyMode) {
                activateEmergencyMode(direction);
                return;
            }
        }

        // Deactivate emergency mode if no more emergency vehicles
        if (emergencyMode && emergencyDirection != null) {
            Road emergencyRoad = intersection.getRoad(emergencyDirection);
            if (!emergencyRoad.hasEmergencyVehicles()) {
                deactivateEmergencyMode();
            }
        }
    }

    private void activateEmergencyMode(Direction emergencyDirection) {
        this.emergencyMode = true;
        this.emergencyDirection = emergencyDirection;
        this.phaseTimer = EMERGENCY_GREEN_TIME;
        this.phaseState = PhaseState.GREEN;

        // Set emergency direction to green, all others to red
        for (Direction direction : Direction.values()) {
            if (direction == emergencyDirection) {
                trafficLights.get(direction).setState(TrafficLightState.GREEN, EMERGENCY_GREEN_TIME);
            } else {
                trafficLights.get(direction).setState(TrafficLightState.RED, EMERGENCY_GREEN_TIME);
            }
        }
    }

    private void deactivateEmergencyMode() {
        this.emergencyMode = false;
        this.emergencyDirection = null;
        // Resume normal operation
        startPhase();
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
        if (emergencyMode) {
            // In emergency mode, just maintain the current state
            activateEmergencyMode(emergencyDirection);
            return;
        }

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
        currentPhase = selectNextPhase();
        startPhase();
    }

    private TrafficPhase selectNextPhase() {
        // Intelligent phase selection based on waiting vehicles
        int nsWeight = intersection.getRoad(Direction.NORTH).calculateWeight() +
                intersection.getRoad(Direction.SOUTH).calculateWeight();
        int ewWeight = intersection.getRoad(Direction.EAST).calculateWeight() +
                intersection.getRoad(Direction.WEST).calculateWeight();

        // If current phase has much more weight, continue with it
        if (currentPhase == TrafficPhase.NORTH_SOUTH && nsWeight > ewWeight * 2) {
            return TrafficPhase.NORTH_SOUTH;
        } else if (currentPhase == TrafficPhase.EAST_WEST && ewWeight > nsWeight * 2) {
            return TrafficPhase.EAST_WEST;
        }

        // Otherwise, alternate phases
        return currentPhase.getNext();
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
        int totalWeight = 0;
        int phaseWeight = 0;

        for (Direction direction : Direction.values()) {
            int weight = intersection.getRoad(direction).calculateWeight();
            totalWeight += weight;

            if (currentPhase.isDirectionAllowed(direction)) {
                phaseWeight += weight;
            }
        }

        if (totalWeight == 0) {
            return (int) (trafficMode.getMinGreenTime() * trafficMode.getTimingMultiplier());
        }

        double ratio = (double) phaseWeight / totalWeight;
        int baseTime = trafficMode.getMinGreenTime() +
                (int) (ratio * (trafficMode.getMaxGreenTime() - trafficMode.getMinGreenTime()));

        return (int) Math.max(trafficMode.getMinGreenTime(),
                Math.min(trafficMode.getMaxGreenTime(),
                        baseTime * trafficMode.getTimingMultiplier()));
    }

    public boolean canVehiclesPass(Direction direction) {
        if (emergencyMode) {
            return direction == emergencyDirection;
        }
        return trafficLights.get(direction).canVehiclesPass();
    }

    public void setTrafficMode(TrafficMode mode) {
        this.trafficMode = mode;
    }

    public TrafficMode getTrafficMode() {
        return trafficMode;
    }

    public boolean isEmergencyMode() {
        return emergencyMode;
    }

    public Direction getEmergencyDirection() {
        return emergencyDirection;
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
        sb.append("  Mode: ").append(trafficMode).append("\n");
        if (emergencyMode) {
            sb.append("  EMERGENCY MODE: ").append(emergencyDirection).append("\n");
        } else {
            sb.append("  Phase: ").append(currentPhase).append(" (").append(phaseState).append(")\n");
        }
        sb.append("  Timer: ").append(phaseTimer).append("\n");
        for (Map.Entry<Direction, TrafficLight> entry : trafficLights.entrySet()) {
            sb.append("  ").append(entry.getValue()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}