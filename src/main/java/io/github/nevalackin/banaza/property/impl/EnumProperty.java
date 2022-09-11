package io.github.nevalackin.banaza.property.impl;

import com.google.gson.JsonObject;
import io.github.nevalackin.banaza.property.Property;

public final class EnumProperty<E extends Enum<?>> extends Property<E> {

    private final E[] variants;

    public EnumProperty(String name, E value) {
        super(name, value);
        this.variants = (E[]) value.getClass().getEnumConstants();
    }

    public E[] getVariants() {
        return variants;
    }

    public void setValueByIndex(int index)  {
        this.setValue(this.variants[index]);
    }

    @Override
    public void write(JsonObject object) {
        object.addProperty(this.getName(), this.getValue().ordinal());
    }

    @Override
    public void read(JsonObject object) {
        if (object.has(this.getName())) {
            this.setValueByIndex(object.getAsJsonPrimitive(this.getName()).getAsInt());
        }
    }
}
