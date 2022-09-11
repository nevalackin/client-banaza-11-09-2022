package io.github.nevalackin.banaza.property.impl;

import com.google.gson.JsonObject;
import io.github.nevalackin.banaza.property.Property;

public final class StringProperty extends Property<String> {

    public StringProperty(String name, String value) {
        super(name, value);
    }

    @Override
    public void write(JsonObject object) {
        object.addProperty(this.getName(), this.getValue());
    }

    @Override
    public void read(JsonObject object) {
        if (object.has(this.getName())) {
            this.setValue(object.getAsJsonPrimitive(this.getName()).getAsString());
        }
    }
}
