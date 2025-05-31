package com.traffic.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class TrafficLightTest {

    private TrafficLight trafficLight;

    @BeforeEach
    void setUp() {
        trafficLight = new TrafficLight(Direction.NORTH);
    }

    @Test
    void testInitialState() {
        assertEquals(Direction.NORTH, trafficLight.getDirection());
        assertEquals(TrafficLightState.RED, trafficLight.getState());
        assertEquals(0, trafficLight.getRemainingTime());
        assertFalse(trafficLight.canVehiclesPass());
        assertTrue(trafficLight.isTimeExpired());
    }

    @Test
    void testSetGreenState() {
        trafficLight.setState(TrafficLightState.GREEN, 30);

        assertEquals(TrafficLightState.GREEN, trafficLight.getState());
        assertEquals(30, trafficLight.getRemainingTime());
        assertTrue(trafficLight.canVehiclesPass());
        assertFalse(trafficLight.isTimeExpired());
    }

    @Test
    void testSetYellowState() {
        trafficLight.setState(TrafficLightState.YELLOW, 3);

        assertEquals(TrafficLightState.YELLOW, trafficLight.getState());
        assertEquals(3, trafficLight.getRemainingTime());
        assertFalse(trafficLight.canVehiclesPass());
        assertFalse(trafficLight.isTimeExpired());
    }

    @Test
    void testSetRedState() {
        trafficLight.setState(TrafficLightState.RED, 10);

        assertEquals(TrafficLightState.RED, trafficLight.getState());
        assertEquals(10, trafficLight.getRemainingTime());
        assertFalse(trafficLight.canVehiclesPass());
        assertFalse(trafficLight.isTimeExpired());
    }

    @Test
    void testDecrementTime() {
        trafficLight.setState(TrafficLightState.GREEN, 5);

        trafficLight.decrementTime();
        assertEquals(4, trafficLight.getRemainingTime());
        assertFalse(trafficLight.isTimeExpired());

        trafficLight.decrementTime();
        trafficLight.decrementTime();
        trafficLight.decrementTime();
        trafficLight.decrementTime();

        assertEquals(0, trafficLight.getRemainingTime());
        assertTrue(trafficLight.isTimeExpired());

        trafficLight.decrementTime();
        assertEquals(0, trafficLight.getRemainingTime());
    }

    @Test
    void testCanVehiclesPassOnlyGreen() {
        trafficLight.setState(TrafficLightState.RED, 10);
        assertFalse(trafficLight.canVehiclesPass());

        trafficLight.setState(TrafficLightState.YELLOW, 3);
        assertFalse(trafficLight.canVehiclesPass());

        trafficLight.setState(TrafficLightState.GREEN, 30);
        assertTrue(trafficLight.canVehiclesPass());
    }
}