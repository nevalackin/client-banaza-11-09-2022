package io.github.nevalackin.banaza.config;

import com.google.gson.JsonObject;

public interface ConfigObject {

    void write(JsonObject object);
    void read(JsonObject object);

}
