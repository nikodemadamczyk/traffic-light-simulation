package com.traffic.command;

import com.traffic.simulation.SimulationContext;

public class StepCommand implements Command {

    @Override
    public void execute(SimulationContext context) {
        context.executeStep();
        int stepNumber = context.getStepResults().size();
        var lastStepResult = context.getStepResults().get(stepNumber - 1);

        System.out.println("Step " + stepNumber + " executed. Vehicles left: " + lastStepResult);
        context.printCurrentState();
    }
}