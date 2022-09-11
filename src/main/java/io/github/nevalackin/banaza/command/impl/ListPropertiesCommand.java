package io.github.nevalackin.banaza.command.impl;

import io.github.nevalackin.banaza.BanazaMod;
import io.github.nevalackin.banaza.command.Command;
import io.github.nevalackin.banaza.module.Module;
import io.github.nevalackin.banaza.property.Property;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public final class ListPropertiesCommand extends Command {
    public ListPropertiesCommand() {
        super("listprops <module>", "Lists properties.", "listprops", "lp", "props", "listproperties");
    }

    @Override
    public void processCommand(String[] args) {
        if (args.length < 2) return;
        BanazaMod.getModuleManager().getModule(args[1]).ifPresent(module -> {
            StringBuilder sb = new StringBuilder("\247a" + args[1] + "'s properties:\n\247d");
            for (Property<?> property : module.getProperties()) {
                sb.append(property.getName()).append(", ");
            }
            MinecraftClient.getInstance().getMessageHandler().onGameMessage(Text.of(sb.toString()), false);
        });

    }
}
