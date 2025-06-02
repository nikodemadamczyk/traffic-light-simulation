package com.traffic.controller;

import com.traffic.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class TrafficControllerAdvancedTest {

    private Intersection intersection;
    private TrafficLightController controller;

    @BeforeEach
    void setUp() {
        intersection = new Intersection();
        controller = new TrafficLightController(intersection, TrafficMode.NORMAL);
    }

    @Test
    void testEmergencyModeActivation() {
        Vehicle emergency = new Vehicle("ambulance1", Direction.WEST, Direction.EAST, VehicleType.EMERGENCY);
        intersection.addVehicle(emergency);

        assertFalse(controller.isEmergencyMode());

        controller.update();

        assertTrue(controller.isEmergencyMode());
        assertEquals(Direction.WEST, controller.getEmergencyDirection());
        assertTrue(controller.canVehiclesPass(Direction.WEST));
        assertFalse(controller.canVehiclesPass(Direction.NORTH));
        assertFalse(controller.canVehiclesPass(Direction.SOUTH));
        assertFalse(controller.canVehiclesPass(Direction.EAST));
    }

    @Test
    void testEmergencyModeDeactivation() {

        Vehicle emergency = new Vehicle("ambulance1", Direction.WEST, Direction.EAST, VehicleType.EMERGENCY);
        intersection.addVehicle(emergency);

        controller.update();
        assertTrue(controller.isEmergencyMode());

        intersection.processVehiclesFromRoad(Direction.WEST, 5);

        controller.update();
        assertFalse(controller.isEmergencyMode());
        assertNull(controller.getEmergencyDirection());
    }

    @Test
    void testTrafficModeChanges() {
        assertEquals(TrafficMode.NORMAL, controller.getTrafficMode());

        controller.setTrafficMode(TrafficMode.RUSH_HOUR);
        assertEquals(TrafficMode.RUSH_HOUR, controller.getTrafficMode());

        controller.setTrafficMode(TrafficMode.NIGHT);
        assertEquals(TrafficMode.NIGHT, controller.getTrafficMode());
    }

    @Test
    void testIntelligentPhaseSelection() {

        for (int i = 0; i < 5; i++) {
            intersection.addVehicle(new Vehicle("west" + i, Direction.WEST, Direction.EAST, VehicleType.CAR));
            intersection.addVehicle(new Vehicle("east" + i, Direction.EAST, Direction.WEST, VehicleType.CAR));
        }


        intersection.addVehicle(new Vehicle("north1", Direction.NORTH, Direction.SOUTH, VehicleType.CAR));

        int westWeight = intersection.getRoad(Direction.WEST).calculateWeight();
        int eastWeight = intersection.getRoad(Direction.EAST).calculateWeight();
        int northWeight = intersection.getRoad(Direction.NORTH).calculateWeight();
        int southWeight = intersection.getRoad(Direction.SOUTH).calculateWeight();

        int ewWeight = westWeight + eastWeight;
        int nsWeight = northWeight + southWeight;

        assertTrue(ewWeight > nsWeight, "East-West should have more weight");
        assertEquals(10, ewWeight);
        assertEquals(1, nsWeight);
    }
}