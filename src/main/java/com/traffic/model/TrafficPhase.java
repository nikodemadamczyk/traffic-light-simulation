package com.traffic.model;

import java.util.Arrays;
import java.util.List;

public enum TrafficPhase {
    NORTH_SOUTH(Arrays.asList(Direction.NORTH, Direction.SOUTH)),
    EAST_WEST(Arrays.asList(Direction.EAST, Direction.WEST));

    private final List<Direction> allowedDirections;

    TrafficPhase(List<Direction> allowedDirections) {
        this.allowedDirections = allowedDirections;
    }

    public List<Direction> getAllowedDirections() {
        return allowedDirections;
    }

    public boolean isDirectionAllowed(Direction direction) {
        return allowedDirections.contains(direction);
    }

    public TrafficPhase getNext() {
        return this == NORTH_SOUTH ? EAST_WEST : NORTH_SOUTH;
    }
}
