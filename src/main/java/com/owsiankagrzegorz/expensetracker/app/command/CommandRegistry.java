package com.owsiankagrzegorz.expensetracker.app.command;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {

    private final Map<Integer, Command> commands = new HashMap<>();
    private final Command fallback;

    public CommandRegistry(Command fallback) {
        this.fallback = fallback;
    }

    public void register(int key, Command command) {
        commands.put(key, command);
    }

    public Command get(int key) {
        return commands.getOrDefault(key, fallback);
    }
}