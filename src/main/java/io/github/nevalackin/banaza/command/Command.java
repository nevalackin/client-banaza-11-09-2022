package io.github.nevalackin.banaza.command;

public abstract class Command {

    private final String[] aliases;
    private final String usage, description;

    public Command(String usage, String description, String... aliases) {
        this.usage = usage;
        this.description = description;
        this.aliases = aliases;
    }

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }

    public abstract void processCommand(String[] args);

}
