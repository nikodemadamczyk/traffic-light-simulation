package com.traffic;

import com.traffic.simulation.SimulationEngine;

public class TrafficSimulationApp {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No arguments provided - running internal test mode...");
            runTestMode();
            return;
        }

        if (args.length == 1 && "test".equals(args[0])) {
            System.out.println("Running internal test mode...");
            runTestMode();
            return;
        }

        if (args.length != 2) {
            System.err.println("Usage: TrafficSimulationApp <input.json> <output.json>");
            System.err.println("   or: TrafficSimulationApp test");
            System.err.println("   or: TrafficSimulationApp (no args for test)");
            System.exit(1);
        }

        String inputFile = args[0];
        String outputFile = args[1];

        try {
            System.out.println("Starting simulation...");
            System.out.println("Input file: " + inputFile);
            System.out.println("Output file: " + outputFile);

            SimulationEngine engine = new SimulationEngine();
            engine.runSimulation(inputFile, outputFile);

            System.out.println("Simulation completed successfully.");

        } catch (Exception e) {
            System.err.println("Error during simulation execution: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void runTestMode() {
        SimulationEngine engine = new SimulationEngine();
        engine.runTestMode();
    }
}