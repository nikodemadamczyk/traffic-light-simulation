package com.traffic.validation;

import com.traffic.command.AddVehicleCommand;
import com.traffic.json.CommandParser;
import com.traffic.json.JsonCommand;
import com.traffic.model.Direction;
import com.traffic.model.Vehicle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {

    @Test
    void testNullVehicleId() {
        assertThrows(NullPointerException.class, () ->
                new Vehicle(null, Direction.NORTH, Direction.SOUTH));
    }

    @Test
    void testEmptyVehicleId() {
        assertDoesNotThrow(() ->
                new Vehicle("", Direction.NORTH, Direction.SOUTH));
    }

    @Test
    void testNullDirectionsInVehicle() {
        assertThrows(NullPointerException.class, () ->
                new Vehicle("car1", null, Direction.SOUTH));

        assertThrows(NullPointerException.class, () ->
                new Vehicle("car1", Direction.NORTH, null));
    }

    @Test
    void testNullDirectionFromString() {
        assertThrows(IllegalArgumentException.class, () ->
                Direction.fromString(null));
    }

    @Test
    void testEmptyDirectionFromString() {
        assertThrows(IllegalArgumentException.class, () ->
                Direction.fromString(""));
    }

    @Test
    void testWhitespaceDirectionFromString() {
        assertThrows(IllegalArgumentException.class, () ->
                Direction.fromString("   "));
    }

    @Test
    void testAddVehicleCommandWithNullValues() {
        assertThrows(IllegalArgumentException.class, () ->
                new AddVehicleCommand("car1", null, "south"));

        assertThrows(IllegalArgumentException.class, () ->
                new AddVehicleCommand("car1", "north", null));
    }

    @Test
    void testCommandParserWithNullCommand() {
        assertThrows(NullPointerException.class, () ->
                CommandParser.parseCommand(null));
    }

    @Test
    void testCommandParserWithNullType() {
        JsonCommand command = new JsonCommand();
        command.setType(null);

        assertThrows(NullPointerException.class, () ->
                CommandParser.parseCommand(command));
    }

    @Test
    void testCommandParserWithEmptyType() {
        JsonCommand command = new JsonCommand();
        command.setType("");

        assertThrows(IllegalArgumentException.class, () ->
                CommandParser.parseCommand(command));
    }

    @Test
    void testAddVehicleCommandMissingFields() {
        JsonCommand command = new JsonCommand();
        command.setType("addVehicle");
        command.setVehicleId("car1");
        command.setStartRoad("north");

        assertThrows(IllegalArgumentException.class, () ->
                CommandParser.parseCommand(command));
    }

    @Test
    void testVehicleEqualsWithNull() {
        Vehicle vehicle = new Vehicle("car1", Direction.NORTH, Direction.SOUTH);

        assertNotEquals(vehicle, null);
        assertNotEquals(null, vehicle);
    }

    @Test
    void testVehicleEqualsWithDifferentClass() {
        Vehicle vehicle = new Vehicle("car1", Direction.NORTH, Direction.SOUTH);
        String notAVehicle = "not a vehicle";

        assertNotEquals(vehicle, notAVehicle);
    }

    @Test
    void testVehicleHashCodeConsistency() {
        Vehicle vehicle1 = new Vehicle("car1", Direction.NORTH, Direction.SOUTH);
        Vehicle vehicle2 = new Vehicle("car1", Direction.EAST, Direction.WEST);

        assertEquals(vehicle1.hashCode(), vehicle2.hashCode());

        for (int i = 0; i < 100; i++) {
            assertEquals(vehicle1.hashCode(), vehicle1.hashCode());
        }
    }
}