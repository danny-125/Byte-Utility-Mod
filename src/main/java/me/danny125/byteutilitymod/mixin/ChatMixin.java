package me.danny125.byteutilitymod.mixin;

import me.danny125.byteutilitymod.ByteUtilityMod;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ChatMixin {
    @Inject(method = "sendChatMessage", at = @At("HEAD"))
    private void onSendChatMessage(String message, CallbackInfo ci) {
        ByteUtilityMod.LOGGER.info("Chat message sent: " + message);
        handleChatMessage(message);
        
    }
    private void handleChatMessage(String message) {
        if (message.equalsIgnoreCase("#helloworld")) {
            ByteUtilityMod.LOGGER.info("Hello World!");
        }
    }
}
