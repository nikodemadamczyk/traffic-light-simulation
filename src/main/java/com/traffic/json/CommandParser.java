package com.traffic.json;

import com.traffic.command.AddVehicleCommand;
import com.traffic.command.Command;
import com.traffic.command.SetTrafficModeCommand;
import com.traffic.command.StepCommand;

public class CommandParser {

    public static Command parseCommand(JsonCommand jsonCommand) {
        if (jsonCommand == null) {
            throw new NullPointerException("JsonCommand cannot be null");
        }

        String type = jsonCommand.getType();
        if (type == null) {
            throw new NullPointerException("Command type cannot be null");
        }

        type = type.trim();
        if (type.isEmpty()) {
            throw new IllegalArgumentException("Command type cannot be empty");
        }

        switch (type.toLowerCase()) {
            case "addvehicle":
                if (jsonCommand.getVehicleId() == null ||
                        jsonCommand.getStartRoad() == null ||
                        jsonCommand.getEndRoad() == null) {
                    throw new IllegalArgumentException("AddVehicle command requires vehicleId, startRoad, and endRoad");
                }

                if (jsonCommand.getVehicleType() != null) {
                    return new AddVehicleCommand(
                            jsonCommand.getVehicleId(),
                            jsonCommand.getStartRoad(),
                            jsonCommand.getEndRoad(),
                            jsonCommand.getVehicleType()
                    );
                } else {
                    return new AddVehicleCommand(
                            jsonCommand.getVehicleId(),
                            jsonCommand.getStartRoad(),
                            jsonCommand.getEndRoad()
                    );
                }

            case "step":
                return new StepCommand();

            case "settrafficmode":
                if (jsonCommand.getTrafficMode() == null) {
                    throw new IllegalArgumentException("SetTrafficMode command requires trafficMode");
                }
                return new SetTrafficModeCommand(jsonCommand.getTrafficMode());

            default:
                throw new IllegalArgumentException("Unknown command type: " + type);
        }
    }
}