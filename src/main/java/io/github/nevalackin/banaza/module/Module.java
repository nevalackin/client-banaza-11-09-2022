package io.github.nevalackin.banaza.module;

import com.google.gson.JsonObject;
import io.github.nevalackin.banaza.BanazaMod;
import io.github.nevalackin.banaza.config.ConfigObject;
import io.github.nevalackin.banaza.property.Property;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public abstract class Module implements ConfigObject {

    private final String name, description;
    private final ModuleCategory category;
    private boolean enabled;

    private int keybindCode = GLFW.GLFW_KEY_UNKNOWN;

    private final List<Property<?>> properties = new ArrayList<>();

    public ModuleCategory getCategory() {
        return category;
    }

    public List<Property<?>> getProperties() {
        return properties;
    }

    public Module(String name, String description, ModuleCategory category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public int getKeybind() {
        return keybindCode;
    }

    public void setKeybind(int keybindCode) {
        this.keybindCode = keybindCode;
    }

    // this is cringe barely builder pattern hopefully i dont lose points
    public Module addProperty(Property<?> property) {
        this.properties.add(property);
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;

            if (enabled) {
                this.onEnable();
                BanazaMod.getEventBus().subscribe(this);
            } else {
                BanazaMod.getEventBus().unsubscribe(this);
                this.onDisable();
            }
        }
    }

    @Override
    public void write(JsonObject object) {
        JsonObject moduleObj = new JsonObject();
        moduleObj.addProperty("enabled", this.isEnabled());
        JsonObject propertiesObj = new JsonObject();
        for (Property<?> property : this.getProperties()) {
            property.write(propertiesObj);
        }
        object.add("properties", propertiesObj);
        object.add(this.getName(), moduleObj);
    }

    @Override
    public void read(JsonObject object) {
        if (object.has(this.getName())) {
            JsonObject moduleObj = object.getAsJsonObject(this.getName());
            this.setEnabled(moduleObj.getAsJsonPrimitive("enabled").getAsBoolean());
            JsonObject propertiesObj = object.getAsJsonObject("properties");
            for (Property<?> property : this.getProperties()) {
                property.read(propertiesObj);
            }
        }
    }

    public abstract void onEnable();

    public abstract void onDisable();

}
