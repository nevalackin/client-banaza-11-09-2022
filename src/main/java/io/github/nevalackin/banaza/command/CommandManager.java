package io.github.nevalackin.banaza.command;

import io.github.nevalackin.banaza.command.impl.ConfigCommand;
import io.github.nevalackin.banaza.command.impl.HelpCommand;
import io.github.nevalackin.banaza.command.impl.ListPropertiesCommand;
import io.github.nevalackin.banaza.command.impl.SetPropertyCommand;

import java.util.*;

public final class CommandManager {

    private static final String PREFIX = ".";
    private final List<Command> commands = new ArrayList<>();
    private final Map<String, Command> aliasMap = new HashMap<>();

    public CommandManager() {
        this.addCommand(new HelpCommand())
                .addCommand(new ConfigCommand())
                .addCommand(new ListPropertiesCommand())
                .addCommand(new SetPropertyCommand());
    }

    public boolean processChatMessage(String message) {
        if (!message.startsWith(PREFIX)) return false;
        message = message.substring(1);
        // save perf
        String[] firstSplitOnly = message.split(" ", 2);
        Command command = this.aliasMap.get(firstSplitOnly[0]);
        if (command != null) {
            // full split
            command.processCommand(message.split(" "));
            return true;
        }
        return false;
    }

    public List<Command> getCommands() {
        return this.commands;
    }

    public CommandManager addCommand(Command command) {
        this.commands.add(command);
        for (String alias : command.getAliases()) {
            this.aliasMap.put(alias, command);
        }
        return this;
    }
}
