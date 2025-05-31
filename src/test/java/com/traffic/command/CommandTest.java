package com.traffic.command;

import com.traffic.model.Direction;
import com.traffic.simulation.SimulationContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    private SimulationContext context;

    @BeforeEach
    void setUp() {
        context = new SimulationContext();
    }

    @Test
    void testAddVehicleCommandCreation() {
        AddVehicleCommand command = new AddVehicleCommand("car1", "north", "south");

        assertEquals("car1", command.getVehicleId());
        assertEquals(Direction.NORTH, command.getStartRoad());
        assertEquals(Direction.SOUTH, command.getEndRoad());
    }

    @Test
    void testAddVehicleCommandInvalidDirection() {
        assertThrows(IllegalArgumentException.class, () ->
                new AddVehicleCommand("car1", "invalid", "south"));

        assertThrows(IllegalArgumentException.class, () ->
                new AddVehicleCommand("car1", "north", "invalid"));
    }

    @Test
    void testAddVehicleCommandExecution() {
        AddVehicleCommand command = new AddVehicleCommand("testCar", "west", "east");

        assertEquals(0, context.getIntersection().getWaitingVehiclesCount(Direction.WEST));

        command.execute(context);

        assertEquals(1, context.getIntersection().getWaitingVehiclesCount(Direction.WEST));
    }

    @Test
    void testStepCommandExecution() {
        StepCommand command = new StepCommand();

        assertEquals(0, context.getStepResults().size());

        command.execute(context);

        assertEquals(1, context.getStepResults().size());
        assertNotNull(context.getStepResults().get(0));
    }

    @Test
    void testStepCommandWithVehicles() {
        AddVehicleCommand addCommand = new AddVehicleCommand("car1", "north", "south");
        StepCommand stepCommand = new StepCommand();

        addCommand.execute(context);
        stepCommand.execute(context);

        assertEquals(1, context.getStepResults().size());
        assertEquals(1, context.getStepResults().get(0).size());
        assertEquals("car1", context.getStepResults().get(0).get(0));
    }

    @Test
    void testMultipleSteps() {
        AddVehicleCommand addCommand1 = new AddVehicleCommand("car1", "north", "south");
        AddVehicleCommand addCommand2 = new AddVehicleCommand("car2", "south", "north");
        StepCommand stepCommand = new StepCommand();

        addCommand1.execute(context);
        addCommand2.execute(context);
        stepCommand.execute(context);
        stepCommand.execute(context);

        assertEquals(2, context.getStepResults().size());
        assertEquals(2, context.getStepResults().get(0).size());
        assertTrue(context.getStepResults().get(0).contains("car1"));
        assertTrue(context.getStepResults().get(0).contains("car2"));
        assertEquals(0, context.getStepResults().get(1).size());
    }
}