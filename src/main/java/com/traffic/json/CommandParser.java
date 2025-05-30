package com.traffic.json;

import com.traffic.command.AddVehicleCommand;
import com.traffic.command.Command;
import com.traffic.command.StepCommand;

public class CommandParser {

    public static Command parseCommand(JsonCommand jsonCommand) {
        String type = jsonCommand.getType();

        switch (type.toLowerCase()) {
            case "addvehicle":
                return new AddVehicleCommand(
                        jsonCommand.getVehicleId(),
                        jsonCommand.getStartRoad(),
                        jsonCommand.getEndRoad()
                );
            case "step":
                return new StepCommand();
            default:
                throw new IllegalArgumentException("Unknown command type: " + type);
        }
    }
}