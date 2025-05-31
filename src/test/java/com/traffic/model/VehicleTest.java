package com.traffic.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void testStraightMovement() {
        Vehicle vehicle1 = new Vehicle("v1", Direction.NORTH, Direction.SOUTH);
        assertEquals(TurnType.STRAIGHT, vehicle1.getTurnType());

        Vehicle vehicle2 = new Vehicle("v2", Direction.SOUTH, Direction.NORTH);
        assertEquals(TurnType.STRAIGHT, vehicle2.getTurnType());

        Vehicle vehicle3 = new Vehicle("v3", Direction.EAST, Direction.WEST);
        assertEquals(TurnType.STRAIGHT, vehicle3.getTurnType());

        Vehicle vehicle4 = new Vehicle("v4", Direction.WEST, Direction.EAST);
        assertEquals(TurnType.STRAIGHT, vehicle4.getTurnType());
    }

    @Test
    void testTurnMovement() {
        Vehicle vehicle1 = new Vehicle("v1", Direction.NORTH, Direction.EAST);
        assertEquals(TurnType.TURN, vehicle1.getTurnType());

        Vehicle vehicle2 = new Vehicle("v2", Direction.NORTH, Direction.WEST);
        assertEquals(TurnType.TURN, vehicle2.getTurnType());

        Vehicle vehicle3 = new Vehicle("v3", Direction.SOUTH, Direction.EAST);
        assertEquals(TurnType.TURN, vehicle3.getTurnType());

        Vehicle vehicle4 = new Vehicle("v4", Direction.WEST, Direction.NORTH);
        assertEquals(TurnType.TURN, vehicle4.getTurnType());
    }

    @Test
    void testInvalidMovement() {
        assertThrows(IllegalArgumentException.class, () ->
                new Vehicle("v1", Direction.NORTH, Direction.NORTH));

        assertThrows(IllegalArgumentException.class, () ->
                new Vehicle("v2", Direction.SOUTH, Direction.SOUTH));

        assertThrows(IllegalArgumentException.class, () ->
                new Vehicle("v3", Direction.EAST, Direction.EAST));

        assertThrows(IllegalArgumentException.class, () ->
                new Vehicle("v4", Direction.WEST, Direction.WEST));
    }

    @Test
    void testVehicleProperties() {
        Vehicle vehicle = new Vehicle("testCar", Direction.NORTH, Direction.SOUTH);

        assertEquals("testCar", vehicle.getVehicleId());
        assertEquals(Direction.NORTH, vehicle.getStartRoad());
        assertEquals(Direction.SOUTH, vehicle.getEndRoad());
        assertEquals(TurnType.STRAIGHT, vehicle.getTurnType());
    }

    @Test
    void testEqualsAndHashCode() {
        Vehicle vehicle1 = new Vehicle("car1", Direction.NORTH, Direction.SOUTH);
        Vehicle vehicle2 = new Vehicle("car1", Direction.EAST, Direction.WEST);
        Vehicle vehicle3 = new Vehicle("car2", Direction.NORTH, Direction.SOUTH);

        assertEquals(vehicle1, vehicle2);
        assertNotEquals(vehicle1, vehicle3);
        assertEquals(vehicle1.hashCode(), vehicle2.hashCode());
    }

    @Test
    void testToString() {
        Vehicle vehicle = new Vehicle("testVehicle", Direction.WEST, Direction.SOUTH);
        String result = vehicle.toString();

        assertTrue(result.contains("testVehicle"));
        assertTrue(result.contains("WEST"));
        assertTrue(result.contains("SOUTH"));
        assertTrue(result.contains("TURN"));
    }
}