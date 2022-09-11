package io.github.nevalackin.banaza.property.impl;

import com.google.gson.JsonObject;
import io.github.nevalackin.banaza.property.Property;

public final class BooleanProperty extends Property<Boolean> {
    public BooleanProperty(String name, boolean value) {
        super(name, value);
    }

    @Override
    public void write(JsonObject object) {
        object.addProperty(this.getName(), this.getValue());
    }

    @Override
    public void read(JsonObject object) {
        if (object.has(this.getName())) {
            this.setValue(object.getAsJsonPrimitive(this.getName()).getAsBoolean());
        }
    }

    @Override
    public void parseAndSetValue(String input) {
        this.setValue(Boolean.parseBoolean(input));
    }
}
