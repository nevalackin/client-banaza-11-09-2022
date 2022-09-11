package io.github.nevalackin.banaza.command.impl;

import io.github.nevalackin.banaza.BanazaMod;
import io.github.nevalackin.banaza.command.Command;
import io.github.nevalackin.banaza.command.CommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public final class HelpCommand extends Command {
    public HelpCommand() {
        super("help <no_args>", "Displays information about all available commands.", "help", "h");
    }

    @Override
    public void processCommand(String[] args) {
        StringBuilder sb = new StringBuilder("\247aAvailable commands:\n");
        for (Command command : BanazaMod.getCommandManager().getCommands()) {
            sb.append("  \247c");

            for (String alias : command.getAliases()) {
                sb.append(alias).append(", ");
            }

            sb.append("\n    \247d")
                    .append(command.getDescription())
                    .append("\n    \247b")
                    .append(command.getUsage())
                    .append("\n\247r");
        }
        MinecraftClient.getInstance().getMessageHandler().onGameMessage(Text.of(sb.toString()), false);
    }
}
