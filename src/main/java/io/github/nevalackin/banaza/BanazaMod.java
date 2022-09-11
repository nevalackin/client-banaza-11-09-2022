package io.github.nevalackin.banaza;

import io.github.nevalackin.banaza.command.CommandManager;
import io.github.nevalackin.banaza.config.ConfigManager;
import io.github.nevalackin.banaza.event.Event;
import io.github.nevalackin.banaza.module.ModuleManager;
import io.github.nevalackin.radbus.PubSub;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class BanazaMod implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("banaza");
    private static ConfigManager configManager;
    private static CommandManager commandManager;
    private static ModuleManager moduleManager;
    private static PubSub<Event> eventBus;

    public static PubSub<Event> getEventBus() {
        if (eventBus == null) {
            throw new IllegalStateException("ERROR BanazaMod#getEventBus(): was called before onInitializeClient().");
        }

        return eventBus;
    }

    public static ModuleManager getModuleManager() {
        if (moduleManager == null) {
            throw new IllegalStateException("ERROR BanazaMod#getModuleManager(): was called before onInitializeClient().");
        }

        return moduleManager;
    }

    public static ConfigManager getConfigManager() {
        if (configManager == null) {
            throw new IllegalStateException("ERROR BanazaMod#getConfigManager(): was called before onInitializeClient().");
        }

        return configManager;
    }

    public static CommandManager getCommandManager() {
        if (commandManager == null) {
            throw new IllegalStateException("ERROR BanazaMod#getCommandManager(): was called before onInitializeClient().");
        }

        return commandManager;
    }

    public static Path getDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static ModMetadata getMetadata() {
        return FabricLoader.getInstance().getModContainer("banaza")
                .orElseThrow()
                .getMetadata();
    }

    @Override
    public void onInitializeClient() {
        eventBus = PubSub.newInstance(LOGGER::error);
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        configManager = new ConfigManager(getDirectory().resolve("banaza-configs"), moduleManager);
    }
}
