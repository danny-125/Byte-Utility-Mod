package me.danny125.byteutilitymod.mixin.client;

import me.danny125.byteutilitymod.ByteUtilityMod;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ChatMixin {
    @Inject(method = "method_3142", at = @At("HEAD"), cancellable = true)
    private void onSendChatMessage(String message, CallbackInfo ci) {
        ByteUtilityMod.LOGGER.info("Chat message sent by player: " + message);
        handleChatMessage(message);
    }

    private void handleChatMessage(String message) {
        if (message.equalsIgnoreCase("#helloworld")) {
            ByteUtilityMod.LOGGER.info("Hello World!");
        }
    }
}