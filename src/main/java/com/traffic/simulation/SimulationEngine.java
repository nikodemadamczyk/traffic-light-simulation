package com.traffic.simulation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traffic.command.Command;
import com.traffic.json.*;
import com.traffic.model.Direction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {
    private final ObjectMapper objectMapper;
    private final SimulationContext context;

    public SimulationEngine() {
        this.objectMapper = new ObjectMapper();
        this.context = new SimulationContext();
    }

    public void runSimulation(String inputFile, String outputFile) throws IOException {
        System.out.println("=== Starting Traffic Light Simulation ===");

        JsonInput input = readInputFile(inputFile);

        System.out.println("Processing " + input.getCommands().size() + " commands...\n");

        for (JsonCommand jsonCommand : input.getCommands()) {
            Command command = CommandParser.parseCommand(jsonCommand);
            command.execute(context);
            System.out.println();
        }

        writeOutputFile(outputFile);

        System.out.println("=== Simulation Complete ===");
        System.out.println("Results written to: " + outputFile);
    }

    private JsonInput readInputFile(String inputFile) throws IOException {
        return objectMapper.readValue(new File(inputFile), JsonInput.class);
    }

    private void writeOutputFile(String outputFile) throws IOException {
        JsonOutput output = new JsonOutput();
        List<StepStatus> stepStatuses = new ArrayList<>();

        for (List<String> stepResult : context.getStepResults()) {
            stepStatuses.add(new StepStatus(stepResult));
        }

        output.setStepStatuses(stepStatuses);

        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(outputFile), output);
    }

    public void runTestMode() {
        System.out.println("=== Running Test Mode ===");
        context.printCurrentState();

        context.addVehicle("vehicle1", Direction.SOUTH, Direction.NORTH);
        context.addVehicle("vehicle2", Direction.NORTH, Direction.SOUTH);
        context.addVehicle("vehicle3", Direction.WEST, Direction.SOUTH);
        context.addVehicle("vehicle4", Direction.WEST, Direction.SOUTH);

        System.out.println("\nAdded test vehicles");
        context.printCurrentState();

        for (int i = 1; i <= 15; i++) {
            System.out.println("\n--- Step " + i + " ---");
            context.executeStep();
        }

        System.out.println("\n=== Test Complete ===");
    }
}