package com.traffic.simulation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traffic.json.JsonInput;
import com.traffic.json.JsonOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

class SimulationEngineTest {

    private SimulationEngine engine;
    private ObjectMapper objectMapper;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        engine = new SimulationEngine();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testBasicSimulationFlow(@TempDir Path tempDir) throws IOException {
        File inputFile = tempDir.resolve("input.json").toFile();
        File outputFile = tempDir.resolve("output.json").toFile();

        String inputJson = "{\n" +
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

        objectMapper.writeValue(inputFile, objectMapper.readValue(inputJson, JsonInput.class));

        assertDoesNotThrow(() -> engine.runSimulation(inputFile.getPath(), outputFile.getPath()));

        assertTrue(outputFile.exists());

        JsonOutput output = objectMapper.readValue(outputFile, JsonOutput.class);
        assertEquals(1, output.getStepStatuses().size());
        assertEquals(1, output.getStepStatuses().get(0).getLeftVehicles().size());
        assertEquals("car1", output.getStepStatuses().get(0).getLeftVehicles().get(0));
    }

    @Test
    void testMultipleVehiclesSimulation(@TempDir Path tempDir) throws IOException {
        File inputFile = tempDir.resolve("input.json").toFile();
        File outputFile = tempDir.resolve("output.json").toFile();

        String inputJson = "{\n" +
                "  \"commands\": [\n" +
                "    {\n" +
                "      \"type\": \"addVehicle\",\n" +
                "      \"vehicleId\": \"car1\",\n" +
                "      \"startRoad\": \"north\",\n" +
                "      \"endRoad\": \"south\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"addVehicle\",\n" +
                "      \"vehicleId\": \"car2\",\n" +
                "      \"startRoad\": \"south\",\n" +
                "      \"endRoad\": \"north\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"step\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"addVehicle\",\n" +
                "      \"vehicleId\": \"car3\",\n" +
                "      \"startRoad\": \"west\",\n" +
                "      \"endRoad\": \"east\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"step\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        objectMapper.writeValue(inputFile, objectMapper.readValue(inputJson, JsonInput.class));

        engine.runSimulation(inputFile.getPath(), outputFile.getPath());

        JsonOutput output = objectMapper.readValue(outputFile, JsonOutput.class);
        assertEquals(2, output.getStepStatuses().size());

        assertEquals(2, output.getStepStatuses().get(0).getLeftVehicles().size());
        assertTrue(output.getStepStatuses().get(0).getLeftVehicles().contains("car1"));
        assertTrue(output.getStepStatuses().get(0).getLeftVehicles().contains("car2"));

        assertEquals(0, output.getStepStatuses().get(1).getLeftVehicles().size());
    }

    @Test
    void testPhaseTransitionSimulation(@TempDir Path tempDir) throws IOException {
        File inputFile = tempDir.resolve("input.json").toFile();
        File outputFile = tempDir.resolve("output.json").toFile();

        String jsonBuilder = "{\"commands\":[" +
                "{\"type\":\"addVehicle\",\"vehicleId\":\"car1\",\"startRoad\":\"west\",\"endRoad\":\"east\"}";

        for (int i = 0; i < 16; i++) {
            jsonBuilder += ",{\"type\":\"step\"}";
        }
        jsonBuilder += "]}";

        objectMapper.writeValue(inputFile, objectMapper.readValue(jsonBuilder, JsonInput.class));

        engine.runSimulation(inputFile.getPath(), outputFile.getPath());

        JsonOutput output = objectMapper.readValue(outputFile, JsonOutput.class);
        assertEquals(16, output.getStepStatuses().size());

        boolean carPassed = false;
        for (int i = 0; i < output.getStepStatuses().size(); i++) {
            if (!output.getStepStatuses().get(i).getLeftVehicles().isEmpty()) {
                assertTrue(output.getStepStatuses().get(i).getLeftVehicles().contains("car1"));
                carPassed = true;
                break;
            }
        }
        assertTrue(carPassed, "Car should have passed after phase transition");
    }

    @Test
    void testInvalidInputFile() {
        assertThrows(IOException.class, () ->
                engine.runSimulation("nonexistent.json", "output.json"));
    }

    @Test
    void testEmptyCommandsList(@TempDir Path tempDir) throws IOException {
        File inputFile = tempDir.resolve("input.json").toFile();
        File outputFile = tempDir.resolve("output.json").toFile();

        String inputJson = "{\"commands\":[]}";
        objectMapper.writeValue(inputFile, objectMapper.readValue(inputJson, JsonInput.class));

        assertDoesNotThrow(() -> engine.runSimulation(inputFile.getPath(), outputFile.getPath()));

        JsonOutput output = objectMapper.readValue(outputFile, JsonOutput.class);
        assertEquals(0, output.getStepStatuses().size());
    }
}