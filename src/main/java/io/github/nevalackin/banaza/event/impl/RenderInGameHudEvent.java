package io.github.nevalackin.banaza.event.impl;

import io.github.nevalackin.banaza.event.Event;
import net.minecraft.client.util.math.MatrixStack;

public final class RenderInGameHudEvent implements Event {

    private final MatrixStack matrices;
    private final float tickDelta;

    public RenderInGameHudEvent(MatrixStack matrices, float tickDelta) {
        this.matrices = matrices;
        this.tickDelta = tickDelta;
    }

    public MatrixStack getMatrices() {
        return matrices;
    }

    public float getTickDelta() {
        return tickDelta;
    }
}
