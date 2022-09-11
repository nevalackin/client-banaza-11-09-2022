package io.github.nevalackin.banaza.module.impl;

import io.github.nevalackin.banaza.BanazaMod;
import io.github.nevalackin.banaza.event.impl.RenderInGameHudEvent;
import io.github.nevalackin.banaza.module.Module;
import io.github.nevalackin.banaza.module.ModuleCategory;
import io.github.nevalackin.banaza.module.ModuleManager;
import io.github.nevalackin.banaza.property.impl.BooleanProperty;
import io.github.nevalackin.banaza.property.impl.DoubleProperty;
import io.github.nevalackin.banaza.property.impl.EnumProperty;
import io.github.nevalackin.banaza.property.impl.StringProperty;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;

public class HudModule extends Module {

    private final BooleanProperty showWatermarkProperty = new BooleanProperty("Show watermark", true);
    private final StringProperty watermarkTextProperty = new StringProperty("Watermark text", "Banaza client");
    private final DoubleProperty textOpacityProperty = new DoubleProperty("Text opacity", 1.0, 0.0, 1.0, 0.1);
    private final EnumProperty<ArraylistColorMode> arraylistColorProperty = new EnumProperty<>("Arraylist color", ArraylistColorMode.BLUE);

    public HudModule() {
        super("Hud", "In game overlay displays enabled modules, watermark, etc.", ModuleCategory.RENDER);

        this.addProperty(this.showWatermarkProperty)
                .addProperty(this.watermarkTextProperty)
                .addProperty(this.textOpacityProperty)
                .addProperty(this.arraylistColorProperty);

        this.setEnabled(true);

        this.setKeybind(GLFW.GLFW_KEY_R);
    }

    private static int colorWithOpacity(int baseColor, double opacity) {
        return baseColor | ((int) (opacity * 0xFF) << 24);
    }


    @Listen
    public void onRenderInGameHud(RenderInGameHudEvent event) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        if (this.showWatermarkProperty.getValue()) {
            textRenderer.drawWithShadow(event.getMatrices(), this.watermarkTextProperty.getValue(), 2.f, 2.f, 0xFF32a852);
        }

        Window window = MinecraftClient.getInstance().getWindow();

        float y = 2.f;

        for (Module module : BanazaMod.getModuleManager().getModules()) {
            if (module.isEnabled()) {
                textRenderer.drawWithShadow(event.getMatrices(), module.getName(), window.getScaledWidth() - 2.f - textRenderer.getWidth(module.getName()), y,
                        colorWithOpacity(this.arraylistColorProperty.getValue().baseColor, this.textOpacityProperty.getValue()));
                y += textRenderer.fontHeight + 2.f;
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    private enum ArraylistColorMode {
        BLUE(0x0000FF),
        RED(0xFF0000),
        GREEN(0x00FF00);

        private final int baseColor;

        ArraylistColorMode(int baseColor) {
            this.baseColor = baseColor;
        }
    }
}
