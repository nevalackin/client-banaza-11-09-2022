package io.github.nevalackin.banaza.module;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.gson.JsonObject;
import io.github.nevalackin.banaza.BanazaMod;
import io.github.nevalackin.banaza.config.ConfigObject;
import io.github.nevalackin.banaza.event.impl.KeyPressEvent;
import io.github.nevalackin.banaza.module.impl.HudModule;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.IntStream;

public final class ModuleManager implements ConfigObject {

    private final ClassToInstanceMap<Module> moduleClassMap;

    public ModuleManager() {
        this.moduleClassMap = ImmutableClassToInstanceMap.<Module>builder()
                .put(HudModule.class, new HudModule())
                .build();

        BanazaMod.getEventBus().subscribe(KeyPressEvent.class, event -> {
            for (Module module : this.getModules()) {
                if (event.getKeyPress() == module.getKeybind()) {
                    module.toggle();
                    break; // no multiple binds on 1 modules
                }
            }
        });
    }

    public Optional<Module> getModule(String name) {
        for (Module module : this.getModules()) {
            if (module.getName().equalsIgnoreCase(name)) {
                return Optional.of(module);
            }
        }

        return Optional.empty();
    }

    public Optional<Module> getModule(Class<? extends Module> clazz) {
        return Optional.ofNullable(this.moduleClassMap.get(clazz));
    }

    public Collection<Module> getModules() {
        return this.moduleClassMap.values();
    }

    @Override
    public void write(JsonObject object) {
        JsonObject modulesObj = new JsonObject();
        for (Module module : this.getModules()) {
            module.write(modulesObj);
        }
        object.add("modules", modulesObj);
    }

    @Override
    public void read(JsonObject object) {
        if (object.has("modules")) {
            for (Module module : this.getModules()) {
                module.read(object.getAsJsonObject("modules"));
            }
        }
    }
}
