package io.github.nevalackin.banaza.mixin;

import io.github.nevalackin.banaza.BanazaMod;
import io.github.nevalackin.banaza.event.impl.RenderInGameHudEvent;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/MinecraftClient;getLastFrameDuration()F"
            ),
            method = "render"
    )
    private void injectInGameHudRender(MatrixStack matrices, float tickDelta, CallbackInfo info) {
        BanazaMod.getEventBus().publish(new RenderInGameHudEvent(matrices, tickDelta));
    }
}
