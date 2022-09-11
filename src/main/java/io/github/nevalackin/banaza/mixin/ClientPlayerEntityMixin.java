package io.github.nevalackin.banaza.mixin;

import io.github.nevalackin.banaza.BanazaMod;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(
            method = "sendChatMessage",
            at = @At("HEAD"),
            cancellable = true
    )
    public void injectSendChatMessage(String message, Text preview, CallbackInfo ci) {
        if (BanazaMod.getCommandManager().processChatMessage(message)) ci.cancel();
    }
}
