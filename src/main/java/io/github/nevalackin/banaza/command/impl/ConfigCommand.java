package io.github.nevalackin.banaza.command.impl;

import io.github.nevalackin.banaza.BanazaMod;
import io.github.nevalackin.banaza.command.Command;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public final class ConfigCommand extends Command {

    public ConfigCommand() {
        super("config <load/save/list/refresh> <config(only for load and save)>", "Perform config related operations.", "config", "c");
    }

    @Override
    public void processCommand(String[] args) {
        // fuck it, whole method is performed asynchronously
        MinecraftClient.getInstance().execute(() -> {
            if (args.length < 2) return;
            switch (args[1].toLowerCase()) {
                case "refresh":
                    BanazaMod.getConfigManager().refresh();
                case "list":
                    StringBuilder sb = new StringBuilder("\247aAvailable configs:\n  \247d");
                    for (String configName : BanazaMod.getConfigManager().getConfigs()) {
                        sb.append(configName).append(", ");
                    }
                    MinecraftClient.getInstance().getMessageHandler().onGameMessage(Text.of(sb.toString()), false);
                    return;
                case "load":
                    if (args.length < 3) return;
                    boolean loaded = BanazaMod.getConfigManager().load(args[2]);
                    MinecraftClient.getInstance().getMessageHandler().onGameMessage(Text.of(loaded ? ("\247aSuccessfully loaded config: " + args[2]) : ("\247cUnable to load config: " + args[2])), false);
                    return;
                case "save":
                    if (args.length < 3) return;
                    boolean saved = BanazaMod.getConfigManager().save(args[2]);
                    MinecraftClient.getInstance().getMessageHandler().onGameMessage(Text.of(saved ? ("\247aSuccessfully saved config: " + args[2]) : ("\247cUnable to save config: " + args[2])), false);
                    return;
            }
        });
    }
}
