package com.traffic.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class RoadTest {

    private Road road;
    private Vehicle vehicle1;
    private Vehicle vehicle2;
    private Vehicle vehicle3;

    @BeforeEach
    void setUp() {
        road = new Road(Direction.NORTH);
        vehicle1 = new Vehicle("v1", Direction.NORTH, Direction.SOUTH);
        vehicle2 = new Vehicle("v2", Direction.NORTH, Direction.SOUTH);
        vehicle3 = new Vehicle("v3", Direction.NORTH, Direction.EAST);
    }

    @Test
    void testInitialState() {
        assertEquals(Direction.NORTH, road.getDirection());
        assertEquals(0, road.getWaitingVehiclesCount());
        assertEquals(0, road.getTotalVehiclesCount());
        assertFalse(road.hasWaitingVehicles());
        assertNull(road.peekNextVehicle());
    }

    @Test
    void testAddVehicle() {
        road.addVehicle(vehicle1);

        assertEquals(1, road.getWaitingVehiclesCount());
        assertEquals(1, road.getTotalVehiclesCount());
        assertTrue(road.hasWaitingVehicles());
        assertEquals(vehicle1, road.peekNextVehicle());
    }

    @Test
    void testAddMultipleVehicles() {
        road.addVehicle(vehicle1);
        road.addVehicle(vehicle2);
        road.addVehicle(vehicle3);

        assertEquals(3, road.getWaitingVehiclesCount());
        assertEquals(3, road.getTotalVehiclesCount());
        assertEquals(vehicle1, road.peekNextVehicle());
    }

    @Test
    void testAddVehicleWrongDirection() {
        Vehicle wrongVehicle = new Vehicle("wrong", Direction.SOUTH, Direction.NORTH);

        assertThrows(IllegalArgumentException.class, () -> road.addVehicle(wrongVehicle));
    }

    @Test
    void testRemoveVehiclesFIFO() {
        road.addVehicle(vehicle1);
        road.addVehicle(vehicle2);
        road.addVehicle(vehicle3);

        List<Vehicle> removed = road.removeVehicles(2);

        assertEquals(2, removed.size());
        assertEquals(vehicle1, removed.get(0));
        assertEquals(vehicle2, removed.get(1));
        assertEquals(1, road.getWaitingVehiclesCount());
        assertEquals(3, road.getTotalVehiclesCount());
        assertEquals(vehicle3, road.peekNextVehicle());
    }

    @Test
    void testRemoveMoreThanAvailable() {
        road.addVehicle(vehicle1);

        List<Vehicle> removed = road.removeVehicles(5);

        assertEquals(1, removed.size());
        assertEquals(vehicle1, removed.get(0));
        assertEquals(0, road.getWaitingVehiclesCount());
        assertNull(road.peekNextVehicle());
    }

    @Test
    void testRemoveFromEmptyRoad() {
        List<Vehicle> removed = road.removeVehicles(3);

        assertTrue(removed.isEmpty());
        assertEquals(0, road.getWaitingVehiclesCount());
    }

    @Test
    void testGetWaitingVehiclesList() {
        road.addVehicle(vehicle1);
        road.addVehicle(vehicle2);

        List<Vehicle> waitingList = road.getWaitingVehiclesList();

        assertEquals(2, waitingList.size());
        assertEquals(vehicle1, waitingList.get(0));
        assertEquals(vehicle2, waitingList.get(1));

        waitingList.clear();
        assertEquals(2, road.getWaitingVehiclesCount());
    }
}