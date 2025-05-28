package main.java.com.traffic;

import main.java.com.traffic.controller.TrafficLightController;
import main.java.com.traffic.model.*;

public class TrafficSimulationApp {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No arguments provided - running internal test mode");
            testSystem();
            return;
        }

        if (args.length == 1 && "test".equals(args[0])) {
            System.out.println("Running internal test mode");
            testSystem();
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

            System.out.println("JSON processing not implemented yet - running test instead:");
            testSystem();

            System.out.println("Simulation completed successfully.");

        } catch (Exception e) {
            System.err.println("Error during simulation execution: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void testSystem() {
        Intersection intersection = new Intersection();
        TrafficLightController controller = new TrafficLightController(intersection);

        System.out.println("Initial state:");
        System.out.println(controller);

        Vehicle v1 = new Vehicle("vehicle1", Direction.SOUTH, Direction.NORTH);
        Vehicle v2 = new Vehicle("vehicle2", Direction.NORTH, Direction.SOUTH);
        Vehicle v3 = new Vehicle("vehicle3", Direction.WEST, Direction.SOUTH);
        Vehicle v4 = new Vehicle("vehicle4", Direction.WEST, Direction.SOUTH);

        intersection.addVehicle(v1);
        intersection.addVehicle(v2);
        intersection.addVehicle(v3);
        intersection.addVehicle(v4);

        System.out.println("\nAfter adding vehicles:");
        System.out.println(intersection);

        for (int step = 1; step <= 15; step++) {
            System.out.println("\nStep " + step);

            controller.update();

            for (Direction dir : controller.getCurrentPhase().getAllowedDirections()) {
                if (controller.canVehiclesPass(dir)) {
                    var removedVehicles = intersection.processVehiclesFromRoad(dir, 2);
                    if (!removedVehicles.isEmpty()) {
                        System.out.println("Vehicles passed from " + dir + ": " + removedVehicles);
                    }
                }
            }

            System.out.println("Controller state: " + controller.getCurrentPhase() +
                    " (" + controller.getPhaseState() + ") Timer: " +
                    controller.getTrafficLight(Direction.NORTH).getRemainingTime());
            System.out.println("Waiting vehicles: N=" + intersection.getWaitingVehiclesCount(Direction.NORTH) +
                    ", S=" + intersection.getWaitingVehiclesCount(Direction.SOUTH) +
                    ", E=" + intersection.getWaitingVehiclesCount(Direction.EAST) +
                    ", W=" + intersection.getWaitingVehiclesCount(Direction.WEST));
        }
    }
}