package main.java.com.traffic.model;

public enum Direction {
    NORTH("north"),
    SOUTH("south"),
    EAST("east"),
    WEST("west");

    private final String jsonName;

    Direction(String jsonName) {
        this.jsonName = jsonName;
    }

    public String getJsonName() {
        return jsonName;
    }

    public static Direction fromString(String direction) {
        for (Direction d : Direction.values()) {
            if (d.jsonName.equalsIgnoreCase(direction)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Unknown direction: " + direction);
    }

    public Direction getOpposite() {
        switch (this) {
            case NORTH: return SOUTH;
            case SOUTH: return NORTH;
            case EAST: return WEST;
            case WEST: return EAST;
            default: throw new IllegalStateException("Unknown direction: " + this);
        }
    }

    public boolean isPerpendicularTo(Direction other) {
        return (this == NORTH || this == SOUTH) && (other == EAST || other == WEST) ||
                (this == EAST || this == WEST) && (other == NORTH || other == SOUTH);
    }
}