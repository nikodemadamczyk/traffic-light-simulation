package main.java.com.traffic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TrafficSimulationApp {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: TrafficSimulationApp <input.json> <output.json>");
            System.exit(1);
        }

        String inputFile = args[0];
        String outputFile = args[1];

        try {
            System.out.println("Starting simulation...");
            System.out.println("Input file: " + inputFile);
            System.out.println("Output file: " + outputFile);

            System.out.println("Simulation completed successfully.");

        } catch (Exception e) {
            System.err.println("Error during simulation execution: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}