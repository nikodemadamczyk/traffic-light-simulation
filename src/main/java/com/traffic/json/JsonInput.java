package com.traffic.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JsonInput {
    @JsonProperty("commands")
    private List<JsonCommand> commands;

    public List<JsonCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<JsonCommand> commands) {
        this.commands = commands;
    }
}