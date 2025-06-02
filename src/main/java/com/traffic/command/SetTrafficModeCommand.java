package com.traffic.command;

import com.traffic.model.TrafficMode;
import com.traffic.simulation.SimulationContext;

public class SetTrafficModeCommand implements Command {
    private final TrafficMode trafficMode;

    public SetTrafficModeCommand(String trafficMode) {
        this.trafficMode = TrafficMode.fromString(trafficMode);
    }

    @Override
    public void execute(SimulationContext context) {
        context.getController().setTrafficMode(trafficMode);
        System.out.println("Traffic mode set to: " + trafficMode);
    }

    public TrafficMode getTrafficMode() {
        return trafficMode;
    }
}