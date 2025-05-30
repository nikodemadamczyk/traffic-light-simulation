package com.traffic.command;

import com.traffic.simulation.SimulationContext;

public interface Command {
    void execute(SimulationContext context);
}