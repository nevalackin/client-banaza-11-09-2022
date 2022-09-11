package io.github.nevalackin.banaza.property.impl;

import com.google.gson.JsonObject;
import io.github.nevalackin.banaza.property.Property;

public final class DoubleProperty extends Property<Double> {

    private final double max, min, inc;

    public DoubleProperty(String name, double value, double min, double max, double inc) {
        super(name, value);
        this.max = max;
        this.min = min;
        this.inc = inc;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public double getInc() {
        return inc;
    }

    @Override
    public void setValue(Double value) {
        super.setValue(Math.max(min, Math.min(max, value)));
    }

    @Override
    public void parseAndSetValue(String input) {
        this.setValue(Double.parseDouble(input));
    }

    @Override
    public void write(JsonObject object) {
        object.addProperty(this.getName(), this.getValue());
    }

    @Override
    public void read(JsonObject object) {
        if (object.has(this.getName())) {
            this.setValue(object.getAsJsonPrimitive(this.getName()).getAsDouble());
        }
    }
}
