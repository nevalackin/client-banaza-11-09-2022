package io.github.nevalackin.banaza.mixin;

import io.github.nevalackin.banaza.BanazaMod;
import io.github.nevalackin.banaza.event.impl.KeyPressEvent;
import io.github.nevalackin.banaza.event.impl.RenderInGameHudEvent;
import net.minecraft.client.Keyboard;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(
            method = "onKey",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/NarratorManager;isActive()Z"
            ),
            cancellable = true
    )
    private void injectOnKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (action == GLFW.GLFW_PRESS) {
            KeyPressEvent event = new KeyPressEvent(key);
            BanazaMod.getEventBus().publish(event);
            // dont send back to minecraft
            if (event.isCancelled()) ci.cancel();
        }
    }
}
