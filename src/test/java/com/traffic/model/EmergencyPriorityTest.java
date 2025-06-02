package com.traffic.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class EmergencyPriorityTest {

    private Road road;

    @BeforeEach
    void setUp() {
        road = new Road(Direction.NORTH);
    }

    @Test
    void testEmergencyVehiclesPriority() {
        Vehicle car = new Vehicle("car1", Direction.NORTH, Direction.SOUTH, VehicleType.CAR);
        Vehicle bus = new Vehicle("bus1", Direction.NORTH, Direction.SOUTH, VehicleType.BUS);
        Vehicle emergency = new Vehicle("ambulance1", Direction.NORTH, Direction.SOUTH, VehicleType.EMERGENCY);

        road.addVehicle(car);
        road.addVehicle(bus);
        road.addVehicle(emergency);

        assertEquals(emergency, road.peekNextVehicle());
        assertTrue(road.hasEmergencyVehicles());
        assertEquals(1, road.getPriorityVehiclesCount());
    }

    @Test
    void testWeightCalculation() {
        Vehicle car = new Vehicle("car1", Direction.NORTH, Direction.SOUTH, VehicleType.CAR);
        Vehicle bus = new Vehicle("bus1", Direction.NORTH, Direction.SOUTH, VehicleType.BUS);
        Vehicle emergency = new Vehicle("ambulance1", Direction.NORTH, Direction.SOUTH, VehicleType.EMERGENCY);

        road.addVehicle(car);
        road.addVehicle(bus);
        road.addVehicle(emergency);

        assertEquals(13, road.calculateWeight());
    }

    @Test
    void testEmergencyProcessingTime() {
        Vehicle emergency = new Vehicle("ambulance1", Direction.NORTH, Direction.SOUTH, VehicleType.EMERGENCY);

        assertEquals(1, emergency.getProcessingTime());
        assertTrue(emergency.hasEmergencyPriority());
    }
}