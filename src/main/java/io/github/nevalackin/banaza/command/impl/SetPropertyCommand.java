package io.github.nevalackin.banaza.command.impl;

import io.github.nevalackin.banaza.BanazaMod;
import io.github.nevalackin.banaza.command.Command;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public final class SetPropertyCommand extends Command {
    public SetPropertyCommand() {
        super("setprop <module> <property> <value>", "Set a property of a module.", "setprop", "sp", "set", "setproperty");
    }

    @Override
    public void processCommand(String[] args) {
        if (args.length < 4) return;
        BanazaMod.getModuleManager().getModule(args[1])
                .flatMap(module -> module.getPropertyByName(args[2]))
                .ifPresent(property -> {
            property.parseAndSetValue(args[3]);
            MinecraftClient.getInstance().getMessageHandler().onGameMessage(Text.of(String.format("\247a%s::%s set to %s", args[1], args[2], property.getValue())), false);
        });
    }
}
