package me.danny125.byteutilitymod.mixin.client;

import me.danny125.byteutilitymod.BYTE;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class GameShutdownMixin {

    @Inject(method = "close", at = @At("HEAD"))
    public void onGameClose(CallbackInfo info) {
        BYTE.INSTANCE.saveConfig("ByteConfig.txt");
    }
}
