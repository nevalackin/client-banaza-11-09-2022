package io.github.nevalackin.banaza.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.nevalackin.banaza.BanazaMod;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Stream;

public final class ConfigManager {

    private final Optional<Path> directory;
    private final Map<String, Path> configs = new HashMap<>();
    private final ConfigObject rootObject;

    private static final Gson GSON = new Gson();

    public ConfigManager(Path directory, ConfigObject rootObject) {
        this.rootObject = rootObject;
        Optional<Path> configsDir;

        try {
            configsDir = Optional.of(Files.createDirectories(directory));
        } catch (IOException e) {
            BanazaMod.LOGGER.error("Unable to create configs directory! Will not be able to save configs.");
            configsDir = Optional.empty();
        }

        this.directory = configsDir;

        this.refresh();
    }

    public Set<String> getConfigs() {
        return this.configs.keySet();
    }

    public void refresh() {
        if (this.directory.isEmpty()) return;
        try (Stream<Path> configFiles = Files.list(this.directory.get())
                .filter(Files::isRegularFile)
                .filter(path -> FilenameUtils.isExtension(path.getFileName().toString(), ".bana"))) {
            configFiles.forEach(path ->
                    this.configs.put(FilenameUtils.removeExtension(path.getFileName().toString()), path));
        } catch (IOException e) {
            BanazaMod.LOGGER.error("Unable to enumerate configs directory! Configs probably won't be available.");
        }
    }

    public boolean load(String config) {
        if (this.directory.isEmpty()) return false;
        Path path = this.configs.get(config);
        if (path == null) return false;
        try {
            try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                JsonObject rootObj = GSON.fromJson(reader, JsonObject.class);
                // load that bitch up (maybe)
                this.rootObject.read(rootObj);
                return true;
            }
        } catch (IOException e) {
            BanazaMod.LOGGER.error("Unable to read config file! Loading configs might not work properly.");
        }
        return false;
    }

    public boolean save(String config) {
        if (this.directory.isEmpty()) return false;
        if (this.configs.containsKey(config)) {
            Path configPath = this.configs.get(config);

            try (Writer writer = Files.newBufferedWriter(configPath, StandardCharsets.UTF_8)) {
                JsonObject rootObj = new JsonObject();
                this.rootObject.write(rootObj);
                // write that cunt down (maybe also)
                writer.write(GSON.toJson(rootObj));
                return true;
            } catch (IOException e) {
                BanazaMod.LOGGER.error("Unable to write config file! Saving configs might not work properly.");
            }
        } else {
            Path configPath = this.directory.get().resolve(config + ".bana");
            try {
                try (Writer writer = Files.newBufferedWriter(configPath, StandardCharsets.UTF_8)) {
                    JsonObject rootObj = new JsonObject();
                    this.rootObject.write(rootObj);
                    // write that cunt down (maybe also)
                    writer.write(GSON.toJson(rootObj));
                    this.configs.put(config, configPath);
                    return true;
                }
            } catch (IOException e) {
                BanazaMod.LOGGER.error("Unable to write config file! Saving configs might not work properly.");
                return false;
            }
        }

        return false;
    }
}
