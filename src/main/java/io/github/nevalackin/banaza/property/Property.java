package io.github.nevalackin.banaza.property;

import io.github.nevalackin.banaza.config.ConfigObject;

public abstract class Property<T> implements ConfigObject {

    private final String name;
    private T value;

    public Property(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public abstract void parseAndSetValue(String input);
}
