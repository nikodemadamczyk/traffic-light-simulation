package com.traffic.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traffic.command.AddVehicleCommand;
import com.traffic.command.Command;
import com.traffic.command.StepCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class JsonTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCommandParserAddVehicle() {
        JsonCommand jsonCommand = new JsonCommand();
        jsonCommand.setType("addVehicle");
        jsonCommand.setVehicleId("car1");
        jsonCommand.setStartRoad("north");
        jsonCommand.setEndRoad("south");

        Command command = CommandParser.parseCommand(jsonCommand);

        assertTrue(command instanceof AddVehicleCommand);
        AddVehicleCommand addCommand = (AddVehicleCommand) command;
        assertEquals("car1", addCommand.getVehicleId());
    }

    @Test
    void testCommandParserStep() {
        JsonCommand jsonCommand = new JsonCommand();
        jsonCommand.setType("step");

        Command command = CommandParser.parseCommand(jsonCommand);

        assertTrue(command instanceof StepCommand);
    }

    @Test
    void testCommandParserCaseInsensitive() {
        JsonCommand jsonCommand1 = new JsonCommand();
        jsonCommand1.setType("ADDVEHICLE");
        jsonCommand1.setVehicleId("car1");
        jsonCommand1.setStartRoad("north");
        jsonCommand1.setEndRoad("south");

        JsonCommand jsonCommand2 = new JsonCommand();
        jsonCommand2.setType("Step");

        assertDoesNotThrow(() -> CommandParser.parseCommand(jsonCommand1));
        assertDoesNotThrow(() -> CommandParser.parseCommand(jsonCommand2));
    }

    @Test
    void testCommandParserInvalidType() {
        JsonCommand jsonCommand = new JsonCommand();
        jsonCommand.setType("invalid");

        assertThrows(IllegalArgumentException.class, () ->
                CommandParser.parseCommand(jsonCommand));
    }

    @Test
    void testStepStatusSerialization() throws Exception {
        StepStatus stepStatus = new StepStatus(Arrays.asList("car1", "car2"));

        String json = objectMapper.writeValueAsString(stepStatus);

        assertTrue(json.contains("leftVehicles"));
        assertTrue(json.contains("car1"));
        assertTrue(json.contains("car2"));
    }

    @Test
    void testStepStatusDeserialization() throws Exception {
        String json = "{\"leftVehicles\":[\"car1\",\"car2\"]}";

        StepStatus stepStatus = objectMapper.readValue(json, StepStatus.class);

        assertEquals(2, stepStatus.getLeftVehicles().size());
        assertTrue(stepStatus.getLeftVehicles().contains("car1"));
        assertTrue(stepStatus.getLeftVehicles().contains("car2"));
    }

    @Test
    void testJsonOutputSerialization() throws Exception {
        JsonOutput output = new JsonOutput();
        List<StepStatus> stepStatuses = Arrays.asList(
                new StepStatus(Arrays.asList("car1")),
                new StepStatus(Arrays.asList()),
                new StepStatus(Arrays.asList("car2", "car3"))
        );
        output.setStepStatuses(stepStatuses);

        String json = objectMapper.writeValueAsString(output);

        assertTrue(json.contains("stepStatuses"));
        assertTrue(json.contains("leftVehicles"));
        assertTrue(json.contains("car1"));
        assertTrue(json.contains("car2"));
        assertTrue(json.contains("car3"));
    }

    @Test
    void testJsonInputDeserialization() throws Exception {
        String json = "{\n" +
                "  \"commands\": [\n" +
                "    {\n" +
                "      \"type\": \"addVehicle\",\n" +
                "      \"vehicleId\": \"car1\",\n" +
                "      \"startRoad\": \"north\",\n" +
                "      \"endRoad\": \"south\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"step\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        JsonInput input = objectMapper.readValue(json, JsonInput.class);

        assertEquals(2, input.getCommands().size());
        assertEquals("addVehicle", input.getCommands().get(0).getType());
        assertEquals("car1", input.getCommands().get(0).getVehicleId());
        assertEquals("step", input.getCommands().get(1).getType());
    }
}